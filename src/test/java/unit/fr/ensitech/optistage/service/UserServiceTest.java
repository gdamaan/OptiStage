package unit.fr.ensitech.optistage.service;

import fr.ensitech.optistage.entity.Question;
import fr.ensitech.optistage.entity.Role;
import fr.ensitech.optistage.entity.User;
import fr.ensitech.optistage.repository.IPasswordHistoryRepository;
import fr.ensitech.optistage.repository.IUserRepository;
import fr.ensitech.optistage.repository.RoleRepository;
import fr.ensitech.optistage.service.UserService;
import lombok.SneakyThrows;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IPasswordHistoryRepository historyRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role role;
    private Question question; // On déclare la question même si elle n'est pas utilisée directement dans les tests, car elle est une dépendance de l'entité User.

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("Étudiant");

        question = new Question();
        question.setId(1);
        question.setQuestion("Nom de votre premier animal ?");

        user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john@gmail.com")
                .password("JohnDoe1234!")
                .response("SecretResponse")
                .role(role)
                .build();
    }

    @After
    public void tearDown() {}

    @SneakyThrows
    @Test
    public void shouldSubscribeUserSuccessfully() {
        //GIVEN
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(null);
        when(roleRepository.getRoleByName(role.getName())).thenReturn(role);

        //WHEN
        boolean success = userService.subscribe(user);

        //THEN
        assertTrue(success);
        verify(userRepository, times(1)).addUser(user);
    }

    @SneakyThrows
    @Test
    public void shouldFailSubscribeWithAlreadyExistingEmail() {
        //GIVEN
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        //WHEN
        boolean success = userService.subscribe(user);

        //THEN
        assertFalse(success);
        verify(userRepository, never()).addUser(any());
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void shouldFailSubscribeWithMissingRole() {
        //GIVEN
        user.setRole(null);
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(null);

        //WHEN
        userService.subscribe(user);

        //THEN
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void shouldFailSubscribeWithMissingRoleName() {
        //GIVEN
        role.setName(null);
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(null);

        //WHEN
        userService.subscribe(user);

        //THEN
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void shouldFailSubscribeWithNonExistingRoleInDatabase() {
        //GIVEN
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(null);
        when(roleRepository.getRoleByName(role.getName())).thenReturn(null);

        //WHEN
        userService.subscribe(user);

        //THEN
    }
}