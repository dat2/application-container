# Example
```java
// App.java
public class App {
  public static void main(String[] args) {
    ApplicationContainer container = new ApplicationContainer();
    container.registerModule(HikariModule.class);
    container.registerComponent(ExampleComponent.class);
    container.registerMain(Main.class);
    container.run(args); // jcommander will auto parse and configure the modules
  }
}
```

```java
public class HikariModule {
  @Config(name = "database.host", description="the computer for the database", required = true)
  private String host;
  
  @Config(name = "database.user", description="the user for the database", required = true)
  private String user;
  
  @Config(name = "database.password", description="the password", required = true)
  private String password;
  
  // Config would be added to an automatically generated jcommander
  
  @Provides
  public HikariDataSource provideDataSource() {
    // jdbc url
    String jdbcUrl = //TODO ;
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setUser(user);
    dataSource.setPassword(password);
  }
  
  // this would be auto generated
  @Override
  public void configure() {
    
  }
  
  // the below would all be generated with the @Config annotation
  @Provides
  @Named("database.host")
  public String provideDatabaseHost() {
    return host;
  }
}
```

```java
@Component
public class ExampleComponent {
  @Inject
  private HikariDataSource dataSource;
  
  public void deleteSomething() {
    // use data source!
  }
}
```

```java
@Component
public void Main {
  @Inject
  private ExampleComponent example;
  
  @Override
  public void onStart() {
    example.deleteSomething();
  }
  
  @Override
  public void onStop() {
    // do cleanup yay
  }
}
```