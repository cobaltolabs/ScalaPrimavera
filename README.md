## Documentation Ver 0.1.5 ##

### Changes on Ver 0.1.5 ###

* `org.scalaprimavera.jdbc.simple.dsl.DSLJdbcTemplate` is template that expose a clean fluent interface to make JDBC Operations

### Changes on Ver 0.1 ###

* Initial version.
* Support for inject `scala.collection.inmutable.List` and `scala.collection.inmutable.Map` in xml configuration.
* `org.scalaprimavera.jdbc.simple.ScalaSimpleJdbcTemplate` is a wrapper for `org.springframework.jdbc.core.simple.SimpleJdbcTemplate` exposing his methods using Scala Collections and others goodies like High-Order Functions and Options

### General ###

#### Maven Repositories ####

The jars will be uploaded to Maven repositories in the next few weeks. You can download the distribution [here](http://www.cobaltolabs.com/index.php/productos/scalaprimavera/)

#### Jar Names ####

The Jar names in ScalaPrimavera have a notation to make clear with versions of Scala and Spring are compiled

The notation is `org.scalaprimavera.[module]-[versions]_[scalaVersion]_[springVersion].jar` Ex: `org.scalaprimavera.jdbc-0.1_291_306.jar` for the jdbc module jar compiled with Scala 2.9.1 and Spring 3.0.6

#### Package Structure ####

The package structure in ScalaPrimavera tries to mirroring the structure of Spring, so the classes for jdbc are in the package `org.scalaprimavera.jdbc`. We make this in order to make more easy to find that classes that you want.

Also in order to avoid classes with the same name, we prefix the classes that have the same name with `Scala` Ex:`ScalaSimpleJdbcTemplate`

#### Logging ####

For logging we're following the [SpringSource recomendations](http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/overview.html#d0e743).


### org.scalaprimavera.beans ###

#### Introduction ####

This module have all the classes that we need to build and inject beans. Spring Framework already have all the classes to build instances of Scala classes, but there's no way to build Scala collections.

#### Creating a Scala List on xml ####

ScalaPrimavera comes with a FactoryBean to build `scala.collections.inmutable.List`

```xml
<bean id="scalaStringsList" class="org.scalaprimavera.beans.factory.config.ScalaListFactoryBean">
    <property name="sourceList">
        <list>
            <value>foo</value>
            <value>bar</value>
        </list>
    </property>
</bean>
```

This will create a bean with type `scala.collections.inmutable.List[String]`

If you want to specify another type rather than `String` you need to add this on the attribute `value-type` of the tag `list`. Ex:

```xml
<bean id="scalaIntList" class="org.scalaprimavera.beans.factory.config.ScalaListFactoryBean">
    <property name="sourceList">
        <list value-type="java.lang.Integer">
            <value>foo</value>
            <value>bar</value>
        </list>
    </property>
</bean>
```

This will create a bean of type `scala.collections.inmutable.List[Int]`.

Also you can build lists of your own Scala or Java classes

```xml
<bean id="scalaUserList" class="org.scalaprimavera.beans.factory.config.ScalaListFactoryBean">
    <property name="sourceList">
        <list value-type="org.scalaprimavera.beans.User">
            <bean class="org.scalaprimavera.beans.User">
                <property name="name" value="John"/>
            </bean>
        </list>
    </property>
</bean>
```

#### Creating a Scala Map on xml ####

In the same way as lists, ScalaPrimavera also have a FactoryBean to build `scala.collections.inmutable.Map`

```xml
<bean id="stringIntMap" class="org.scalaprimavera.beans.factory.config.ScalaMapFactoryBean">
    <property name="sourceMap">
        <map key-type="java.lang.String" value-type="java.lang.Integer">
            <entry key="foo" value="123"/>
            <entry key="bar" value="456"/>
        </map>
    </property>
</bean>
```

#### Namespace Support ####

ScalaPrimavera comes with a xml namespace to build Scala Collections

You only need to configure the namespace 'sp-collections' on your xml, in the same way that the namespaces provided with Spring

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:sp-collections="http://www.scalaprimavera.org/schema/sp-collections"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
           http://www.scalaprimavera.org/schema/sp-collections
           http://www.scalaprimavera.org/schema/collections/sp-collections-1.0.xsd">
    <sp-collections:list id="scalaStringsList" value-type="java.lang.String">
        <value>foo</value>
        <value>bar</value>
    </sp-collections:list>
    <sp-collections:list id="scalaIntList" value-type="java.lang.Integer">
        <value>123</value>
        <value>456</value>
    </sp-collections:list>
    <sp-collections:map id="stringStringMap" key-type="java.lang.String" value-type="java.lang.String">
        <entry key="foo" value="bar"/>
    </sp-collections:map>
    <sp-collections:map id="stringIntMap" key-type="java.lang.String" value-type="java.lang.Integer">
        <entry key="foo" value="123"/>
        <entry key="bar" value="456"/>
    </sp-collections:map>
</beans>
```

#### Support for other collections ####

Right now we're only supporting this two collections, if you want support for other collections, please add an [issue](https://github.com/cobaltolabs/ScalaPrimavera/issues) on GitHub

### org.scalaprimavera.jdbc ###

#### Introduction ####

This modules offer support for using `SimpleJdbcTemplate` inside Scala. Also offer a new class `DSLJdbcTemplate` that expose a clean fluent interface
Support for other classes will come in next releases

#### ScalaSimpleJdbcTemplate ####

`org.scalaprimavera.jdbc.core.simple.ScalaSimpleJdbcTemplate` is a wrapper for [`org.springframework.jdbc.core.simple.SimpleJdbcTemplate`.](http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/jdbc/core/simple/SimpleJdbcTemplate.html)

This wrapper add support for receive and return Scala Collections, also use High-Order functions and Options

##### Creating a ScalaSimpleJdbcTemplate #####

In order to create a `ScalaSimpleJdbcTemplate` you need to inject a bean of type `SimpleJdbcTemplate`

In the next example we'll add an anonymous bean:

```xml
<bean id="template" class="org.scalaprimavera.jdbc.core.simple.ScalaSimpleJdbcTemplate">
    <constructor-arg>
        <bean class="org.springframework.jdbc.core.simple.SimpleJdbcTemplate">
            <constructor-arg type="javax.sql.DataSource" ref="dataSource"/>
        </bean>
    </constructor-arg>
</bean>
```

##### Querying a Int/Long #####

To make a query that returns an Int/Long you must use the method `queryForInt` to `Int` or `queryForLong` to `Long`. Ex:

```scala
val count = template.queryForInt("select count(id) from users")
```

If you want to add parameters to the query you have three options:

First you can add more parameters to this function (The last parameter of this function is `Any*`)

```scala
val count = template.queryForInt("select count(id) from users where age > ?",18)
```

The symbol `?` act as placeholder and will be replaced with `18` just like a `PreparedStatement`. If there's more placeholders they will be replaced in order

```scala
val count = template.queryForInt("select count(id) from users where age > ? and first_name = ?",18,"John")
```


But also you can use a `Map`

```scala
val count = template.queryForInt("select count(id) from users where age > :age and first_name = :name",Map("age" -> 18,"name" -> "John"))
```

Now the placeholders have a name with a prefix `:`, in this case `:age` and `:name`; and they will be replaced for the values in the map with the same key

The last option is to use an instance of [`org.springframework.jdbc.core.namedparam.SqlParameterSource`](http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/jdbc/core/namedparam/SqlParameterSource.html)

```scala
val count = template.queryForInt("select count(id) from users where age > :age", new MapSqlParameterSource("age",18))
```

##### Managing queries without result #####

Is a common scenario that some queries don't return any result, in this cases in Spring don't will return a `null` but a `org.springframework.dao.EmptyResultDataAccessException` will be thrown

```scala
try {
    val age = template.queryForInt("select age from users where id > ?", 4567)
    println("The age for user with id:" + 4567 + " is " + age)
}
catch {
    case e:EmptyResultDataAccessException => println("Don't found data for user with id:" + 4567)
}
```

`ScalaSimpleJdbcTemplate` provide a more elegant way to manage this situations using to the Scala `Option[T]`

```scala
template.queryForOptionInt("select age from users where id > ?", 4567)  match {
    case Some(age) => println("The age for user with id:" + 4567 + " is " + age)
    case None => println("Don't found data for user with id:" + 4567)
}
```

In `ScalaSimpleJdbcTemplate` every function that could be throw an `EmptyResultDataAccessException` has a 'mirror' function that return an `Option[T]`

##### Querying more complex objects #####

We really love `Int`'s but our databases have more types of fields. If we want to retrieve a `String` we can use the `queryForObject` function

```scala
val firstName = template.queryForObject("select first_name from users where id = ?", classOf[String], 4567)
```

You can extract any type that can be converted from SQL types to Java/Scala types

This function is overrided to receiving the query parameters as `Map`, `SqlParameterSource`, and `Any*`.

If you want a function that returns a `Option[T]` you can use `queryForOption`

The most common case is to extract an entity for our domain/model, so you can pass a function to map the data on a `ResultSet` to your object

```scala
val user = template.queryForObject("select * from users where id = ?",
    (set: ResultSet, i: Int) => {
        new User(set.getInt("id"),set.getString("first_name"),set.getString("last_name"),set.getInt("age"))
    }, 4567)
```

The first argument is our query, the second argument is a function of type `(ResultSet, Int) => T`, the third argument is the query parameters; in this case is an `Any*` but could be a `Map` or a `SqlParameterSource`

As you can see the function type is very similar to the method `mapRow` from the class [`org.springframework.jdbc.core.RowMapper`](http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/jdbc/core/RowMapper.html).
Behind de scenes ScalaPrimavera convert this function into an instance of `RowMapper`

If you want to reuse the function, you can declare it as a `val`

```scala
val mapperFunction = (set: ResultSet, i: Int) => {
    new User(set.getInt("id"), set.getString("first_name"), set.getString("last_name"), set.getInt("age"))
}

val user = template.queryForObject("select * from users where id = ?",mapperFunction, 4567)
```

With some Scala tricks you can make the function more concise:

```scala
val mapperFunction = (set: ResultSet, i: Int) => {
    import set._
    new User(getInt("id"), getString("first_name"), getString("last_name"), getInt("age"))
}
```

If you want to retrieve a `List[T]` you can use the function `query`

```scala
val mapperFunction = (set: ResultSet, i: Int) => {
    import set._
    new User(getInt("id"), getString("first_name"), getString("last_name"), getInt("age"))
}

val users:List[User] = template.query("select * from users where",mapperFunction)
```

Sometimes in complex queries the return type don't match any Entity of your domain. In those cases you can use `queryForMap`

```scala
val user = template.queryForMap("select firs_name, age as years from users where id = ?",4567)

println(user("FIRST_NAME") + " is " + user("YEARS") + " years old")
```

`queryForMap` will return a `Map[String,Any]` where the key is the column name

For returning more than one value you can use `queryForList` that will return a `List[Map[String,Any]]`

##### Comparision Table #####

The next table will help you to choose the right function for your query

| Return Type             | Method           | Option Method        |
| ----------------------- | ---------------- | -------------------- |
| `Int`                   | `queryForInt`    | `queryForOptionInt`  |
| `Long`                  | `queryForLong`   | `queryForOptionLong` |
| `T`                     | `queryForObject` | `queryForOption`     |
| `List[T]`               | `query`          | *Non exists*         |
| `Map[String,Any]`       | `queryForMap`    | `queryForOptionMap`  |
| `List[Map[String,Any]]` | `queryForList`   | *Non exists*         |

##### Manipulating data #####

For operations like update, delete, insert and others you can use the function `update`

```scala
template.update("insert into users(first_name,last_name,age) values(?,?,?)", "John", "Doe", 24);
```

Also you can use a `Map` to pass the query parameters

```scala
template.update("insert into users(first_name,last_name,age) values(:name,:lastName,:age)",
                Map("name" -> "John","lastName" -> "Doe", "age" -> 24);
```

This functions will return an `Int` with the count of modified rows

For batch operations you could use the `batchUpdate` function

```scala
template.batchUpdate("delete from users where id = :id",
    Seq(new MapSqlParameterSource("id", 1),
        new MapSqlParameterSource("id", 3),
        new MapSqlParameterSource("id", 4567),
        new MapSqlParameterSource("id", 9078)))
```

Or like this

```scala
template.batchUpdate("insert into users(first_name,last_name,age) values(?,?,?)",
    Seq(Seq("John", "Doe", 24),
        Seq("Jane", "Doe", 22),
        Seq("Fulano", "de Tal", 50),
        Seq("Mike", "Foo", 24)),
        Seq(java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.INTEGER))
```

In the last argument you specify the SQL Type of the parameters, if the parameters have the default type (Like in the past example) you could pass an empty `Seq`

```scala
template.batchUpdate("insert into users(first_name,last_name,age) values(?,?,?)",
    Seq(Seq("John", "Doe", 24),
        Seq("Jane", "Doe", 22),
        Seq("Fulano", "de Tal", 50),
        Seq("Mike", "Foo", 24)),
        Seq.empty)
```

#### DSLJdbcTemplate ####

`org.scalaprimavera.jdbc.core.simple.dsl.DSLJdbcTemplate` is a class that offers a clean fluent interface to make JDBC Operations

##### Creating a DSLJdbcTemplate #####

In order to create a `DSLJdbcTemplate` you need to inject a bean of type `ScalaSimpleJdbcTemplate`

In the next example we'll add an anonymous bean:

```xml
<bean id="template" class="org.scalaprimavera.jdbc.core.simple.dsl.DSLJdbcTemplate">
    <constructor-arg>
        <bean class="org.scalaprimavera.jdbc.core.simple.ScalaSimpleJdbcTemplate">
            <constructor-arg>
                <bean class="org.springframework.jdbc.core.simple.SimpleJdbcTemplate">
                    <constructor-arg type="javax.sql.DataSource" ref="dataSource"/>
                </bean>
            </constructor-arg>
        </bean>
    </constructor-arg>
</bean>
```

##### Querying a Int/Long #####

To make a query that returns an Int/Long you must use the method `queryForInt` to `Int` or `queryForLong` to `Long`. Ex:


```scala
val count = template.queryForInt("select count(id) from users where age > :age")
                    .paramaters("age" -> 18)
```

You can also use infix notation

```scala
val count = template queryForInt "select count(id) from users where age > :age" paramaters "age" -> 18
```

If you need to add more parameters

```scala
val count = template queryForInt "select count(id) from users where age > :age and first_name = :name" parameters("id" -> 18,"name" -> "John")
```

'DSLJdbcTemplate' also offer functions that return `Option` like `ScalaSimpleJdbcTemplate`

##### Querying more complex objects #####

If we want to retrieve a `String` we can use the `queryForObject` function

```scala
val firstName = template queryForObject "select first_name from users where id = :id" parameters "id" -> 4567 as classOf[String]
```

The most common case is to extract an entity for our domain/model

```scala
val user = template queryForObject "select * from users where id = :id" parameters "id" -> 4567 mappedWith {
        (set, i) => {
            new User(set.getInt("id"),set.getString("first_name"),set.getString("last_name"),set.getInt("age"))
        }
    }
```

If you want to reuse the `mappedWith` argument function, you can declare it as a `val`

```scala
val mapperFunction = (set: ResultSet, i: Int) => {
    new User(set.getInt("id"), set.getString("first_name"), set.getString("last_name"), set.getInt("age"))
}

val user = template queryForObject "select * from users where id = :id" parameters "id" -> 4567 mappedWith mapperFunction
```

If you want to retrieve a `List[T]` you can use the function `query`

Sometimes in complex queries the return type don't match any Entity of your domain. In those cases you can use `queryForMap`

```scala
val user = template queryForMap "select firs_name, age as years from users where id = :id" parameters "id" -> 4567

println(user("FIRST_NAME") + " is " + user("YEARS") + " years old")
```

`queryForMap` will return a `Map[String,Any]` where the key is the column name

For returning more than one value you can use `queryForList` that will return a `List[Map[String,Any]]`


##### Manipulating data #####

For operations like update, delete, insert and others you can use the function `update`


```scala
template.update "insert into users(first_name,last_name,age) values(:name,:lastName,:age)" parameters("name" -> "John","lastName" -> "Doe", "age" -> 24)
```

Also you can use an instance of your's model class (Very useful on most MVC applications)

```scala
val user = new User("John", "Doe", 18)

template.update "insert into users(first_name,last_name,age) values(:firstName,:lastName,:age)" withThis user
```
In this case your model class need to have properties with the same name as the query parameters. In this example the 'User' class need to have properties with the name `firstName`, `lastName` and `age`; and, of course, this properties need to have a type that could be converted to SQL types

This functions will return an `Int` with the count of modified rows

##### Fallback #####

If the functions that offer `DSLJdbcTemplate` aren't enough for your needs, you always could fallback to `ScalaSimpleJdbcTemplate` with the method `noDSL`

```scala
val count = template.noDSL.queryForInt("select count(id) from users")
```


## FAQ ##

### What is this? ###

ScalaPrimavera is a project to bring the [Scala Language](http://scala-lang.org) power to the [Spring Framework](http://springsource.org)
world.

Some companies have a heavy investment on the Spring Framework and related projects, but also want to try Scala.
ScalaPrimavera let's they try Scala in a safer way, while learn functional programming concepts that could improve code quality.

### Who is behind this? ###

[Cobalto Labs SAS](http://cobaltolabs.com) is a company based in Bogot&aacute;, Colombia, with a broad experience in Spring development, we deliver SpringSource certified training and consulting across the Americas.

We develop succesful projects using Spring and Scala, and we know that huge impact that this combination could bring to a project.

ScalaPrimavera was developed as our internal library, now we are sharing this with the development community with an open source license (Apache 2.0)

### Do you have a roadmap? ###

Not a detailed one, but we have broad plan in mind:

From the version 0.1.* to the 0.9.* ScalaPrimavera will don't have a stable API. We'll could change the API, in a way that will don't be backwards compatible.
This versions are designed to early adopters and application prototypes (Altough we already use this on production on our projects).

In the version 1.0.* we plan to have a stable API, that could be used in production

The versions 1.\*.\* we like to add some famous Scala libraries to the Spring world

### Why this silly name? ###

*Primavera* is the spanish word for "Spring", in a serie of fortunate events this also a valid word in Italian, Catalan, Portuguese and other languages.
We're very proud of our hispanic heritage, so we want to reflect this in the name of our project
