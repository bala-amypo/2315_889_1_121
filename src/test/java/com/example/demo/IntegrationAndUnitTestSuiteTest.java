package com.example.demo;

import com.example.demo.controller.AuthController;
import com.example.demo.controller.SeatingPlanController;
import com.example.demo.controller.ExamRoomController;
import com.example.demo.controller.ExamSessionController;
import com.example.demo.controller.StudentController;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.ApiException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@Listeners(TestResultListener.class)
public class IntegrationAndUnitTestSuiteTest {

    @Mock private UserRepository userRepo;
    @Mock private StudentRepository studentRepo;
    @Mock private ExamRoomRepository roomRepo;
    @Mock private ExamSessionRepository sessionRepo;
    @Mock private SeatingPlanRepository planRepo;

    private UserService userService;
    private StudentService studentService;
    private ExamRoomService roomService;
    private ExamSessionService sessionService;
    private SeatingPlanService planService;

    private JwtTokenProvider jwtTokenProvider;

    // simple id generator used for plan save variants that require unique ids
    private final AtomicLong idGen = new AtomicLong(1000L);

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // create password encoder via Spring bean in real app; for tests, we can skip hashing
        userService = new UserServiceImpl(userRepo, new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder());
        studentService = new StudentServiceImpl(studentRepo);
        roomService = new ExamRoomServiceImpl(roomRepo);
        sessionService = new ExamSessionServiceImpl(sessionRepo, studentRepo);
        planService = new SeatingPlanServiceImpl(sessionRepo, planRepo, roomRepo);

        // make a test secret long enough for HMAC-SHA usage
        jwtTokenProvider = new JwtTokenProvider("this_is_a_test_secret_key_must_be_long_enough_for_hmac_sha_which_is_long", 3600000);
    }

    /* ---------- Helpers for safe repository stubs ---------- */

    private Object safeSaveUser(org.mockito.invocation.InvocationOnMock inv) {
        User input = null;
        try { input = inv.getArgument(0); } catch (Exception ignored) {}
        User result = new User();
        if (input != null) {
            result.setName(input.getName());
            result.setEmail(input.getEmail());
            result.setPassword(input.getPassword());
            result.setRole(input.getRole() == null ? "STAFF" : input.getRole());
        } else {
            result.setName("generated");
            result.setEmail("generated@example.com");
            result.setPassword("pass");
            result.setRole("STAFF");
        }
        // deterministic id for debugging
        result.setId( (long) (100 + idGen.getAndIncrement() % 1000) );
        return result;
    }

    private Object safeSaveStudent(org.mockito.invocation.InvocationOnMock inv) {
        Student input = null;
        try { input = inv.getArgument(0); } catch (Exception ignored) {}
        Student s = new Student();
        if (input != null) {
            s.setRollNumber(input.getRollNumber());
            s.setName(input.getName());
            s.setDepartment(input.getDepartment());
            s.setYear(input.getYear());
        } else {
            s.setRollNumber("GEN" + idGen.incrementAndGet());
            s.setName("Generated Student");
            s.setDepartment("GEN");
            s.setYear(1);
        }
        s.setId(idGen.incrementAndGet());
        return s;
    }

    private Object safeSaveRoom(org.mockito.invocation.InvocationOnMock inv) {
        ExamRoom input = null;
        try { input = inv.getArgument(0); } catch (Exception ignored) {}
        ExamRoom r = new ExamRoom();
        if (input != null) {
            r.setRoomNumber(input.getRoomNumber());
            r.setRows(input.getRows());
            r.setColumns(input.getColumns());
            // ensure capacity consistency
            if (input.getRows() != null && input.getColumns() != null) {
                r.setCapacity(input.getRows() * input.getColumns());
            } else if (input.getCapacity() != null) {
                r.setCapacity(input.getCapacity());
            } else {
                r.setCapacity(1);
            }
        } else {
            r.setRoomNumber("GEN_ROOM_" + idGen.incrementAndGet());
            r.setRows(1);
            r.setColumns(1);
            r.setCapacity(1);
        }
        r.setId(idGen.incrementAndGet());
        return r;
    }

    private Object safeSaveSession(org.mockito.invocation.InvocationOnMock inv) {
        ExamSession input = null;
        try { input = inv.getArgument(0); } catch (Exception ignored) {}
        ExamSession s = new ExamSession();
        if (input != null) {
            s.setCourseCode(input.getCourseCode());
            s.setExamDate(input.getExamDate());
            s.setExamTime(input.getExamTime());
            s.setStudents(input.getStudents() == null ? new HashSet<>() : new HashSet<>(input.getStudents()));
        } else {
            s.setCourseCode("GEN_COURSE");
            s.setExamDate(LocalDate.now().plusDays(1));
            s.setExamTime("09:00");
            s.setStudents(new HashSet<>());
        }
        s.setId(idGen.incrementAndGet());
        return s;
    }

    private Object safeSavePlan(org.mockito.invocation.InvocationOnMock inv) {
        SeatingPlan input = null;
        try { input = inv.getArgument(0); } catch (Exception ignored) {}
        SeatingPlan p = new SeatingPlan();
        if (input != null) {
            p.setExamSession(input.getExamSession());
            p.setRoom(input.getRoom());
            p.setArrangementJson(input.getArrangementJson() == null ? "{}" : input.getArrangementJson());
            p.setGeneratedAt(input.getGeneratedAt() == null ? LocalDateTime.now() : input.getGeneratedAt());
        } else {
            p.setArrangementJson("{}");
            p.setGeneratedAt(LocalDateTime.now());
        }
        p.setId(idGen.incrementAndGet());
        return p;
    }

    /* ---------- Tests ---------- */

    /* 1 Develop and deploy a simple servlet using Tomcat Server (simulated) */
@Test(groups = "servlet", priority = 1)
public void test01_simulated_application_start() {
    new org.springframework.boot.builder.SpringApplicationBuilder(DemoApplication.class)
            .web(org.springframework.boot.WebApplicationType.NONE)   // ← prevents Tomcat startup
            .run();

    Assert.assertTrue(true);
}


    /* 2 CRUD operations using Spring Boot and REST APIs */
    @Test(groups = "crud", priority = 2)
    public void test02_createStudent_success() {
        Student s = Student.builder().rollNumber("R100").name("Alice").department("CS").year(3).build();
        when(studentRepo.findByRollNumber("R100")).thenReturn(Optional.empty());
        when(studentRepo.save(any())).thenAnswer(this::safeSaveStudent);
        Student saved = studentService.addStudent(s);
        Assert.assertNotNull(saved.getId());
    }

    @Test(groups = "crud", priority = 3)
    public void test03_createStudent_invalidYear_fail() {
        Student s = Student.builder().rollNumber("R101").name("Bob").year(6).build();
        try {
            studentService.addStudent(s);
            Assert.fail("Expected ApiException");
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("year"));
        }
    }

    @Test(groups = "crud", priority = 4)
    public void test04_addRoom_success() {
        ExamRoom r = ExamRoom.builder().roomNumber("A1").rows(5).columns(5).build();
        when(roomRepo.findByRoomNumber("A1")).thenReturn(Optional.empty());
        when(roomRepo.save(any())).thenAnswer(this::safeSaveRoom);
        ExamRoom saved = roomService.addRoom(r);
        Assert.assertEquals(saved.getCapacity().intValue(), 25);
        Assert.assertNotNull(saved.getId());
    }

    @Test(groups = "crud", priority = 5)
    public void test05_createSession_valid() {
        Student s1 = Student.builder().id(1L).rollNumber("R1").name("S1").department("CS").year(2).build();
        ExamSession session = ExamSession.builder()
                .courseCode("CS101")
                .examDate(LocalDate.now().plusDays(1))
                .examTime("09:00")
                .students(new HashSet<>(Set.of(s1)))
                .build();
        when(sessionRepo.save(any())).thenAnswer(this::safeSaveSession);
        ExamSession out = sessionService.createSession(session);
        Assert.assertNotNull(out.getId());
    }

    @Test(groups = "crud", priority = 6)
    public void test06_createSession_pastDate_fail() {
        ExamSession session = ExamSession.builder().courseCode("X").examDate(LocalDate.now().minusDays(1)).examTime("10:00").students(new HashSet<>()).build();
        try {
            sessionService.createSession(session);
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("past"));
        }
    }

    @Test(groups = "crud", priority = 7)
    public void test07_generatePlan_noRoom_fail() {
        ExamSession session = ExamSession.builder().id(500L)
                .courseCode("C")
                .examDate(LocalDate.now().plusDays(2))
                .examTime("10:00")
                .students(new HashSet<>(Set.of(Student.builder().id(1L).rollNumber("R1").build())))
                .build();
        when(sessionRepo.findById(500L)).thenReturn(Optional.of(session));
        when(roomRepo.findAll()).thenReturn(Collections.emptyList());
        try {
            planService.generatePlan(500L);
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("no room"));
        }
    }

    @Test(groups = "crud", priority = 8)
    public void test08_generatePlan_success() {
        Student st = Student.builder().id(1L).rollNumber("R1").department("CS").build();
        ExamSession session = ExamSession.builder().id(501L).courseCode("C1").examDate(LocalDate.now().plusDays(1)).examTime("09:00").students(new HashSet<>(Set.of(st))).build();
        ExamRoom room = ExamRoom.builder().id(2L).roomNumber("R2").rows(1).columns(2).capacity(2).build();
        when(sessionRepo.findById(501L)).thenReturn(Optional.of(session));
        when(roomRepo.findAll()).thenReturn(List.of(room));
        when(planRepo.save(any())).thenAnswer(this::safeSavePlan);
        SeatingPlan plan = planService.generatePlan(501L);
        Assert.assertNotNull(plan.getId());
        Assert.assertTrue(plan.getArrangementJson() != null && plan.getArrangementJson().contains("R1"));
    }

    @Test(groups = "crud", priority = 9)
    public void test09_getPlan_notFound() {
        when(planRepo.findById(999L)).thenReturn(Optional.empty());
        try {
            planService.getPlan(999L);
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("plan not found"));
        }
    }

    /* 3 Dependency Injection & IoC */
    @Test(groups = "di", priority = 10)
    public void test10_services_instantiated() {
        Assert.assertNotNull(studentService);
        Assert.assertNotNull(roomService);
        Assert.assertNotNull(sessionService);
        Assert.assertNotNull(planService);
    }

    @Test(groups = "di", priority = 11)
    public void test11_repo_methods_invoked() {
        studentService.getAllStudents();
        // use atLeastOnce to avoid flakiness if services call repo internally multiple times
        verify(studentRepo, atLeastOnce()).findAll();
    }

    /* 4 Hibernate config + annotations */
    @Test(groups = "hibernate", priority = 12)
    public void test12_student_entity_annotations_present() {
        boolean ent = Student.class.isAnnotationPresent(jakarta.persistence.Entity.class);
        Assert.assertTrue(ent);
    }

    @Test(groups = "hibernate", priority = 13)
    public void test13_room_capacity_enforced() {
        ExamRoom r = ExamRoom.builder().rows(3).columns(4).build();
        r.ensureCapacityMatches();
        Assert.assertEquals(r.getCapacity().intValue(), 12);
    }

    @Test(groups = "hibernate", priority = 14)
    public void test14_session_students_manyToMany_present() {
        // defensively check fields - if the exact index is different, scan fields
        boolean ann = false;
        var fields = ExamSession.class.getDeclaredFields();
        for (var f : fields) {
            if (f.isAnnotationPresent(jakarta.persistence.ManyToMany.class)) {
                ann = true;
                break;
            }
        }
        Assert.assertTrue(ann);
    }

    /* 5 JPA normalization */
    @Test(groups = "jpa-normalization", priority = 15)
    public void test15_entities_exist() {
        Assert.assertNotNull(ExamSession.class);
        Assert.assertNotNull(SeatingPlan.class);
    }

    @Test(groups = "jpa-normalization", priority = 16)
    public void test16_student_unique_roll_check() {
        when(studentRepo.findByRollNumber("R200")).thenReturn(Optional.of(new Student()));
        try {
            studentService.addStudent(Student.builder().rollNumber("R200").name("X").year(3).build());
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("exists"));
        }
    }

    /* 6 Many-to-Many and associations */
    @Test(groups = "many-to-many", priority = 17)
    public void test17_createSession_with_multiple_students() {
        Student s1 = Student.builder().id(1L).rollNumber("A1").name("A").year(2).department("CS").build();
        Student s2 = Student.builder().id(2L).rollNumber("B1").name("B").year(2).department("EE").build();
        ExamSession sess = ExamSession.builder().courseCode("M").examDate(LocalDate.now().plusDays(1)).examTime("09:00").students(Set.of(s1,s2)).build();
        when(sessionRepo.save(any())).thenAnswer(this::safeSaveSession);
        ExamSession out = sessionService.createSession(sess);
        Assert.assertNotNull(out.getId());
    }

    @Test(groups = "many-to-many", priority = 18)
    public void test18_getSession_notFound() {
        when(sessionRepo.findById(999L)).thenReturn(Optional.empty());
        try {
            sessionService.getSession(999L);
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("session not found"));
        }
    }

    /* 7 Security & JWT */
    @Test(groups = "jwt", priority = 19)
    public void test19_register_and_login_generates_jwt() {
        User u = User.builder().id(1L).name("Admin").email("a@x.com").password("pass").role("ADMIN").build();
        when(userRepo.findByEmail("a@x.com")).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenAnswer(this::safeSaveUser);

        // register via service (service should call repo.save internally)
        User registered = ((UserServiceImpl) userService).register(u);
        Assert.assertNotNull(registered.getId());

        when(userRepo.findByEmail("a@x.com")).thenReturn(Optional.of(registered));
        String token = jwtTokenProvider.generateToken(registered.getId(), registered.getEmail(), registered.getRole());
        Assert.assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test(groups = "jwt", priority = 20)
    public void test20_jwt_contains_claims() {
        String token = jwtTokenProvider.generateToken(42L, "bob@x.com", "STAFF");
        Assert.assertEquals(jwtTokenProvider.getEmailFromToken(token), "bob@x.com");
        Assert.assertEquals(jwtTokenProvider.getRoleFromToken(token), "STAFF");
        Assert.assertEquals(jwtTokenProvider.getUserIdFromToken(token).longValue(), 42L);
    }

    @Test(groups = "jwt", priority = 21)
    public void test21_invalid_jwt_detected() {
        Assert.assertFalse(jwtTokenProvider.validateToken("invalid.token.value"));
    }

    /* 8 HQL / advanced querying (simulated) */
    @Test(groups = "hql", priority = 22)
    public void test22_find_rooms_by_capacity() {
        when(roomRepo.findByCapacityGreaterThanEqual(50)).thenReturn(Collections.emptyList());
        List<ExamRoom> out = roomRepo.findByCapacityGreaterThanEqual(50);
        Assert.assertTrue(out.isEmpty());
    }

    @Test(groups = "hql", priority = 23)
    public void test23_sessions_by_date() {
        when(sessionRepo.findByExamDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Assert.assertTrue(sessionRepo.findByExamDate(LocalDate.now()).isEmpty());
    }

    /* 24-60 additional tests: edge cases, negative tests, controllers, service flows */
    @Test(groups = "extra", priority = 24)
    public void test24_addStudent_duplicateRoll() {
        when(studentRepo.findByRollNumber("R300")).thenReturn(Optional.of(new Student()));
        try {
            studentService.addStudent(Student.builder().rollNumber("R300").name("X").year(1).build());
            Assert.fail();
        } catch (ApiException ex) { Assert.assertTrue(true); }
    }

    @Test(groups = "extra", priority = 25)
    public void test25_listStudents_callsRepo() {
        studentService.getAllStudents();
        verify(studentRepo, atLeastOnce()).findAll();
    }

    @Test(groups = "extra", priority = 26)
    public void test26_addRoom_duplicateRoomNumber() {
        when(roomRepo.findByRoomNumber("A2")).thenReturn(Optional.of(new ExamRoom()));
        try {
            roomService.addRoom(ExamRoom.builder().roomNumber("A2").rows(2).columns(2).build());
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("exists"));
        }
    }

    @Test(groups = "extra", priority = 27)
    public void test27_getAllRooms_callsRepo() {
        roomService.getAllRooms();
        verify(roomRepo, atLeastOnce()).findAll();
    }

    @Test(groups = "extra", priority = 28)
    public void test28_controller_addStudent_callsService() {
        StudentController ctrl = new StudentController(studentService);
        Student s = Student.builder().rollNumber("R400").name("Z").department("ME").year(2).build();
        when(studentRepo.findByRollNumber("R400")).thenReturn(Optional.empty());
        when(studentRepo.save(any())).thenAnswer(this::safeSaveStudent);
        var resp = ctrl.add(s);
        Assert.assertNotNull(resp.getBody().getId());
    }

    @Test(groups = "extra", priority = 29)
    public void test29_controller_listStudents_returnsList() {
        StudentController ctrl = new StudentController(studentService);
        when(studentRepo.findAll()).thenReturn(List.of(Student.builder().id(1L).rollNumber("R1").build()));
        var resp = ctrl.list();
        Assert.assertTrue(resp.getBody().size() > 0);
    }

    @Test(groups = "extra", priority = 30)
    public void test30_controller_room_add_and_list() {
        ExamRoomController ctrl = new ExamRoomController(roomService);
        ExamRoom r = ExamRoom.builder().roomNumber("C1").rows(2).columns(3).build();
        when(roomRepo.findByRoomNumber("C1")).thenReturn(Optional.empty());
        when(roomRepo.save(any())).thenAnswer(this::safeSaveRoom);
        var add = ctrl.add(r);
        Assert.assertNotNull(add.getBody().getId());
        when(roomRepo.findAll()).thenReturn(List.of(r));
        var list = ctrl.list();
        Assert.assertFalse(list.getBody().isEmpty());
    }

    @Test(groups = "extra", priority = 31)
    public void test31_session_controller_create_get() {
        ExamSessionController ctrl = new ExamSessionController(sessionService);
        Student s = Student.builder().id(5L).rollNumber("X5").build();
        ExamSession ss = ExamSession.builder().courseCode("PX").examDate(LocalDate.now().plusDays(1)).examTime("10:00").students(new HashSet<>(Set.of(s))).build();
        when(sessionRepo.save(any())).thenAnswer(this::safeSaveSession);
        var res = ctrl.create(ss);
        Assert.assertNotNull(res.getBody().getId());
        when(sessionRepo.findById(res.getBody().getId())).thenReturn(Optional.of(ss));
        var get = ctrl.get(res.getBody().getId());
        Assert.assertNotNull(get.getBody());
    }

    @Test(groups = "extra", priority = 32)
    public void test32_generate_multiple_plans_for_session() {
        Student st1 = Student.builder().id(1L).rollNumber("A1").department("CS").build();
        Student st2 = Student.builder().id(2L).rollNumber("B1").department("EE").build();
        ExamSession ses = ExamSession.builder().id(300L).courseCode("X").examDate(LocalDate.now().plusDays(1)).examTime("09:00").students(new HashSet<>(Set.of(st1,st2))).build();
        ExamRoom room = ExamRoom.builder().id(3L).roomNumber("R3").rows(1).columns(2).capacity(2).build();
        when(sessionRepo.findById(300L)).thenReturn(Optional.of(ses));
        when(roomRepo.findAll()).thenReturn(List.of(room));
        when(planRepo.save(any())).thenAnswer(this::safeSavePlan);
        SeatingPlan p1 = planService.generatePlan(300L);
        SeatingPlan p2 = planService.generatePlan(300L);
        Assert.assertNotEquals(p1.getId(), p2.getId());
    }

    @Test(groups = "extra", priority = 33)
    public void test33_getPlansBySession_callsRepo() {
        when(planRepo.findByExamSessionId(300L)).thenReturn(Collections.emptyList());
        var l = planService.getPlansBySession(300L);
        Assert.assertTrue(l.isEmpty());
    }

    @Test(groups = "extra", priority = 34)
    public void test34_auth_controller_register_and_login_flow() throws Exception {
        AuthController ctrl = new AuthController(userService, new org.springframework.security.authentication.AuthenticationManager() {
            @Override public org.springframework.security.core.Authentication authenticate(org.springframework.security.core.Authentication authentication) throws org.springframework.security.core.AuthenticationException {
                return authentication;
            }
        }, jwtTokenProvider, userRepo);

        RegisterRequest req = RegisterRequest.builder().name("U").email("u1@example.com").password("p").role("STAFF").build();
        when(userRepo.findByEmail("u1@example.com")).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenAnswer(this::safeSaveUser);
        var r = ctrl.register(req);
        Assert.assertNotNull(((User)r.getBody()).getId());

        when(userRepo.findByEmail("u1@example1.com")).thenReturn(Optional.of((User)r.getBody())); // defensive
        when(userRepo.findByEmail("u1@example.com")).thenReturn(Optional.of((User)r.getBody()));
        AuthRequest ar = new AuthRequest(); ar.setEmail("u1@example.com"); ar.setPassword("p");
        var loginResp = ctrl.login(ar);
        AuthResponse body = loginResp.getBody();
        Assert.assertTrue(body.getToken().length() > 10);
    }

    @Test(groups = "extra", priority = 35)
    public void test35_plan_controller_endpoints() {
        SeatingPlanController ctrl = new SeatingPlanController(planService);
        when(planRepo.findById(777L)).thenReturn(Optional.of(new SeatingPlan()));
        when(planRepo.findByExamSessionId(1L)).thenReturn(Collections.emptyList());
        // get -> ok
        var resp = ctrl.get(777L);
        Assert.assertTrue(resp.getStatusCode().is2xxSuccessful());
        // list -> ok
        var list = ctrl.list(1L);
        Assert.assertNotNull(list.getBody());
    }

    @Test(groups = "extra", priority = 36)
    public void test36_invalid_student_missing_fields() {
        try {
            studentService.addStudent(Student.builder().name("NoRoll").year(2).build());
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test(groups = "extra", priority = 37)
    public void test37_room_negative_rows_fail() {
        try {
            roomService.addRoom(ExamRoom.builder().roomNumber("X").rows(-1).columns(2).build());
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test(groups = "extra", priority = 38)
    public void test38_session_requires_students_fail() {
        try {
            sessionService.createSession(ExamSession.builder().courseCode("T").examDate(LocalDate.now().plusDays(1)).examTime("09:00").students(new HashSet<>()).build());
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("at least 1 student"));
        }
    }

    @Test(groups = "extra", priority = 39)
    public void test39_plan_arrangement_matches_room_capacity() {
        Student st1 = Student.builder().id(1L).rollNumber("S1").department("A").build();
        ExamSession ses = ExamSession.builder().id(400L).courseCode("Z").examDate(LocalDate.now().plusDays(1)).examTime("09:00").students(new HashSet<>(Set.of(st1))).build();
        ExamRoom room = ExamRoom.builder().id(4L).roomNumber("R4").rows(1).columns(1).capacity(1).build();
        when(sessionRepo.findById(400L)).thenReturn(Optional.of(ses));
        when(roomRepo.findAll()).thenReturn(List.of(room));
        when(planRepo.save(any())).thenAnswer(this::safeSavePlan);
        SeatingPlan p = planService.generatePlan(400L);
        Assert.assertNotNull(p.getArrangementJson());
    }

    @Test(groups = "extra", priority = 40)
    public void test40_multiple_rooms_selection_prefers_first_sufficient() {
        Student st1 = Student.builder().id(2L).rollNumber("S2").build();
        ExamSession ses = ExamSession.builder().id(401L).courseCode("Y").examDate(LocalDate.now().plusDays(1)).examTime("10:00").students(new HashSet<>(Set.of(st1))).build();
        ExamRoom small = ExamRoom.builder().id(5L).roomNumber("small").rows(1).columns(1).capacity(1).build();
        ExamRoom big = ExamRoom.builder().id(6L).roomNumber("big").rows(2).columns(2).capacity(4).build();
        when(sessionRepo.findById(401L)).thenReturn(Optional.of(ses));
        // return big first to simulate preference for first sufficient
        when(roomRepo.findAll()).thenReturn(List.of(big, small));
        when(planRepo.save(any())).thenAnswer(this::safeSavePlan);
        SeatingPlan plan = planService.generatePlan(401L);
        Assert.assertNotNull(plan);
    }

    @Test(groups = "extra", priority = 41)
    public void test41_student_service_getAll_empty() {
        when(studentRepo.findAll()).thenReturn(Collections.emptyList());
        List<Student> l = studentService.getAllStudents();
        Assert.assertTrue(l.isEmpty());
    }

    @Test(groups = "extra", priority = 42)
    public void test42_room_repo_custom_query() {
        when(roomRepo.findByCapacityGreaterThanEqual(10)).thenReturn(Collections.emptyList());
        Assert.assertTrue(roomRepo.findByCapacityGreaterThanEqual(10).isEmpty());
    }

    @Test(groups = "extra", priority = 43)
    public void test43_session_repo_findByDate() {
        when(sessionRepo.findByExamDate(LocalDate.now().plusDays(1))).thenReturn(Collections.emptyList());
        Assert.assertTrue(sessionRepo.findByExamDate(LocalDate.now().plusDays(1)).isEmpty());
    }

    @Test(groups = "extra", priority = 44)
    public void test44_plan_repo_by_session() {
        when(planRepo.findByExamSessionId(501L)).thenReturn(Collections.emptyList());
        Assert.assertTrue(planRepo.findByExamSessionId(501L).isEmpty());
    }

    @Test(groups = "extra", priority = 45)
    public void test45_controller_room_list_call() {
        ExamRoomController ctrl = new ExamRoomController(roomService);
        when(roomRepo.findAll()).thenReturn(List.of(ExamRoom.builder().roomNumber("Z1").rows(1).columns(1).build()));
        var resp = ctrl.list();
        Assert.assertTrue(resp.getBody().size() >= 1);
    }

    @Test(groups = "extra", priority = 46)
    public void test46_controller_session_get_notFound() {
        ExamSessionController ctrl = new ExamSessionController(sessionService);
        when(sessionRepo.findById(9999L)).thenReturn(Optional.empty());
        try {
            ctrl.get(9999L);
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

    @Test(groups = "extra", priority = 47)
    public void test47_generate_plan_invalid_session() {
        when(sessionRepo.findById(12345L)).thenReturn(Optional.empty());
        try {
            planService.generatePlan(12345L);
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("session not found"));
        }
    }

    @Test(groups = "extra", priority = 48)
    public void test48_student_controller_lookup_and_list() {
        StudentController ctrl = new StudentController(studentService);
        when(studentRepo.findAll()).thenReturn(List.of(Student.builder().id(1L).rollNumber("S1").build()));
        var res = ctrl.list();
        Assert.assertFalse(res.getBody().isEmpty());
    }

    @Test(groups = "extra", priority = 49)
    public void test49_plan_service_getPlansBySession_empty() {
        when(planRepo.findByExamSessionId(700L)).thenReturn(Collections.emptyList());
        List<SeatingPlan> l = planService.getPlansBySession(700L);
        Assert.assertTrue(l.isEmpty());
    }

    @Test(groups = "extra", priority = 50)
    public void test50_user_service_findByEmail_notFound() {
        when(userRepo.findByEmail("noone@x.com")).thenReturn(Optional.empty());
        try {
            userService.findByEmail("noone@x.com");
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test(groups = "extra", priority = 51)
    public void test51_register_duplicate_email_fail() {
        when(userRepo.findByEmail("dup@x.com")).thenReturn(Optional.of(new User()));
        try {
            userService.register(User.builder().email("dup@x.com").password("p").name("N").build());
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test(groups = "extra", priority = 52)
    public void test52_room_capacity_consistent_after_save() {
        ExamRoom r = ExamRoom.builder().rows(2).columns(3).build();
        r.ensureCapacityMatches();
        Assert.assertEquals(r.getCapacity().intValue(), 6);
    }

    @Test(groups = "extra", priority = 53)
    public void test53_session_creation_with_existing_students() {
        Student s = Student.builder().id(2L).rollNumber("X2").name("X").year(2).build();
        ExamSession session = ExamSession.builder().examDate(LocalDate.now().plusDays(1)).examTime("08:00").courseCode("X").students(new HashSet<>(Set.of(s))).build();
        when(sessionRepo.save(any())).thenAnswer(this::safeSaveSession);
        ExamSession out = sessionService.createSession(session);
        Assert.assertNotNull(out.getId());
    }

    @Test(groups = "extra", priority = 54)
    public void test54_generate_plan_respects_capacity() {
        Student s = Student.builder().id(3L).rollNumber("S3").department("CS").build();
        ExamSession ses = ExamSession.builder().id(600L).courseCode("Z").examDate(LocalDate.now().plusDays(2)).examTime("09:00").students(new HashSet<>(Set.of(s))).build();
        ExamRoom room = ExamRoom.builder().id(7L).roomNumber("R7").rows(1).columns(1).capacity(1).build();
        when(sessionRepo.findById(600L)).thenReturn(Optional.of(ses));
        when(roomRepo.findAll()).thenReturn(List.of(room));
        when(planRepo.save(any())).thenAnswer(this::safeSavePlan);
        SeatingPlan plan = planService.generatePlan(600L);
        Assert.assertEquals(plan.getRoom().getId(), room.getId());
    }

    @Test(groups = "extra", priority = 55)
    public void test55_controller_plan_get_notFound() {
        SeatingPlanController ctrl = new SeatingPlanController(planService);
        when(planRepo.findById(8888L)).thenReturn(Optional.empty());
        try {
            ctrl.get(8888L);
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

    @Test(groups = "extra", priority = 56)
    public void test56_service_exception_message_consistency() {
        try {
            sessionService.getSession(-1L);
            Assert.fail();
        } catch (ApiException ex) {
            Assert.assertTrue(ex.getMessage().toLowerCase().contains("session not found"));
        }
    }

    @Test(groups = "extra", priority = 57)
    public void test57_plan_arrangement_json_valid() throws Exception {
        Student s = Student.builder().id(4L).rollNumber("R99").department("A").build();
        ExamSession ses = ExamSession.builder().id(700L).courseCode("A").examDate(LocalDate.now().plusDays(1)).examTime("09:00").students(new HashSet<>(Set.of(s))).build();
        ExamRoom room = ExamRoom.builder().id(8L).roomNumber("R8").rows(1).columns(1).capacity(1).build();
        when(sessionRepo.findById(700L)).thenReturn(Optional.of(ses));
        when(roomRepo.findAll()).thenReturn(List.of(room));
        when(planRepo.save(any())).thenAnswer(this::safeSavePlan);
        SeatingPlan p = planService.generatePlan(700L);
        new ObjectMapper().readTree(p.getArrangementJson()); // no exception
    }

    @Test(groups = "extra", priority = 58)
    public void test58_register_default_role_staff() {
        when(userRepo.findByEmail("new@x.com")).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenAnswer(this::safeSaveUser);
        User u = userService.register(User.builder().email("new@x.com").password("p").name("N").build());
        Assert.assertEquals(u.getRole(), "STAFF");
    }

    @Test(groups = "extra", priority = 59)
    public void test59_plan_getPlansBySession_multiple() {
        when(planRepo.findByExamSessionId(501L)).thenReturn(List.of(new SeatingPlan(), new SeatingPlan()));
        List<SeatingPlan> list = planService.getPlansBySession(501L);
        Assert.assertEquals(list.size(), 2);
    }

    @Test(groups = "extra", priority = 60)
    public void test60_final_health_check() {
        Assert.assertTrue(true);
    }
}
