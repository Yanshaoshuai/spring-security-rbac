### spring-security-rbac
a rbac system implement by spring security 6.1.2 and springboot 3.1.2


### Function:
- form login

- custom json login

- custom redis store login info

- custom dynamic access control

# todo
- RoleHierarchy
- Jwt token
- Frontend
- OAuth2

```text

├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───rbac
│   │   │           └───springsecurity
│   │   │               │   SpringSecurityRbacApplication.java
│   │   │               │
│   │   │               ├───common
│   │   │               │       PageResult.java
│   │   │               │       Result.java
│   │   │               │
│   │   │               ├───config
│   │   │               │       CustomAuthorizationManager.java
│   │   │               │       SecurityConfig.java
│   │   │               │       SwaggerConfig.java
│   │   │               │
│   │   │               ├───controller
│   │   │               │       AdminController.java
│   │   │               │       TestController.java
│   │   │               │       UserController.java
│   │   │               │
│   │   │               ├───filter
│   │   │               │       CaptchaFilter.java
│   │   │               │       JsonLoginFilter.java
│   │   │               │
│   │   │               ├───handler
│   │   │               │       CustomAccessDeniedHandler.java
│   │   │               │       JsonLoginFailureHandler.java
│   │   │               │       JsonLoginSuccessHandler.java
│   │   │               │
│   │   │               ├───mapper
│   │   │               │       PermissionMapper.java
│   │   │               │       RoleMapper.java
│   │   │               │       UserMapper.java
│   │   │               │
│   │   │               ├───pattern
│   │   │               │   ├───Delegate
│   │   │               │   │       BuildingWorker.java
│   │   │               │   │       DelegateWorker.java
│   │   │               │   │       FactoryWorker.java
│   │   │               │   │       Worker.java
│   │   │               │   │
│   │   │               │   └───event
│   │   │               │       ├───java
│   │   │               │       │       CustomEvent.java
│   │   │               │       │       CustomEventListener.java
│   │   │               │       │       EventSourceObject.java
│   │   │               │       │
│   │   │               │       └───spring
│   │   │               │               CustomSpringEvent.java
│   │   │               │               CustomSpringEventListener.java
│   │   │               │               CustomSpringEventPublisher.java
│   │   │               │
│   │   │               ├───pojo
│   │   │               │   └───entity
│   │   │               │           Permission.java
│   │   │               │           Role.java
│   │   │               │           User.java
│   │   │               │
│   │   │               ├───repository
│   │   │               │       RedisContextRepository.java
│   │   │               │       SupplierDeferredSecurityContext.java
│   │   │               │
│   │   │               ├───service
│   │   │               │   │   PermissionService.java
│   │   │               │   │   RoleService.java
│   │   │               │   │   UserService.java
│   │   │               │   │
│   │   │               │   └───impl
│   │   │               │           PermissionServiceImpl.java
│   │   │               │           RoleServiceImpl.java
│   │   │               │           UserServiceImpl.java
│   │   │               │
│   │   │               └───util
│   │   │                       ObjectSerializer.java
│   │   │
│   │   └───resources
│   │       │   application.yaml
│   │       │
│   │       └───mapper
│   │               PermissionMapper.xml
│   │
│   └───test
│       └───java
│           └───com
│               └───rbac
│                   └───springsecurity
│                           AntPathMatcherTest.java
│                           PasswordEncoderTest.java
│                           SpringSecurityRbacApplicationTests.java


```