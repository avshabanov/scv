
## Target-Action-Filter

Specification pattern:

* Create generation code target
* Assign an action to that target
* Filter methods that will be associated with that action

Example:

```java
final PluginTarget<Foo> fooTarget = create(Target.fromClassName("example.FooImpl"), Foo.class);

//<target>  <action>           <filter>
fooTarget.doSomethingCool().forAllMethods()

//<target>  <action>           <filter>     <method filter>
fooTarget.doAnotherCoolThing().forMethod().bar(any(String.class));
```
