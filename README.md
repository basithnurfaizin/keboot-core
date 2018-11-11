# keboot-core
Productivity Wrapper for Spring Boot

# Installation
Add this repository and dependency in your build xml file
```xml
<dependencies>
    <dependency>
        <groupId>id.co.javan.keboot</groupId>
        <artifactId>core</artifactId>
        <version>0.1</version>
    </dependency>
</dependencies>
    
<repositories>
    <repository>
        <id>javan-releases</id>
        <url>https://maven.javan.co.id/repository/maven-releases</url>
    </repository>
</repositories>
```

# Usage

## Using Generator
TO BE Implemented

## Manual
1. Create model class
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    private Date birthDate;
}
```
2. Create form class, extending EntityCrudForm
```java
public class UserForm implements EntityCrudForm<User> {

    @NotEmpty
    private String fullname;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birth_date;

    @Override
    public User toCreateEntity() {
        User user = new User();
        user.setFullname(fullname);
        user.setBirthDate(birth_date);

        return user;
    }

    @Override
    public User toUpdateEntity(User oldEntity) {
        oldEntity.setFullname(fullname);
        oldEntity.setBirthDate(birth_date);

        return oldEntity;
    }
}
```
3. Create repository interface, extending EntityCrudRepository
```java
public interface UserRepository extends EntityCrudRepository<User, Long> {
}
```
4. Create service interface and its implementation
```java
public interface UserService extends EntityCrudService<User, Long, UserForm> {
}

@Service
public class UserServiceImpl extends EntityCrudServiceImpl<User, Long, UserForm, UserRepository> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public Specification<User> getSearchFilter(Object... filters) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.length > 0 && filters[0] != null && !StringUtils.isEmpty(filters[0].toString())) {
                String keyword = filters[0].toString().trim().toLowerCase();
                predicates.add(
                        builder.like(builder.lower(root.get("fullname")), "%" + keyword + "%")
                );
            }
            query.orderBy(builder.desc(root.get("id")));

            return builder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
```
5. Create controller class, extending EntityCrudController
```java
@RestController
public class UserController extends EntityCrudController<User, Long, UserForm> {
    @Autowired
    public UserController(UserService userService) {
        this.entityCrudService = userService;
    }

    @Override
    @GetMapping(value = "/user")
    public Page<User> list(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                           @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return super.list(page, size, keyword);
    }

    @Override
    @PostMapping(value = "/user")
    public User create(HttpServletRequest request, @Valid UserForm userForm, BindingResult bindingResult) throws Exception {
        return super.create(request, userForm, bindingResult);
    }

    @Override
    @GetMapping(value = "/user/{id}")
    public User view(@PathVariable("id") Long id) {

        return super.view(id);
    }

    @Override
    @PostMapping(value = "/user/{id}")
    public User update(@PathVariable("id") Long id, HttpServletRequest request, @Valid UserForm userForm, BindingResult bindingResult) throws Exception {

        return super.update(id, request, userForm, bindingResult);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Map> delete(@PathVariable("id") Long id) {

        return super.delete(id);
    }
}
```

## Sample Project
https://github.com/javanlabs/keboot-sample