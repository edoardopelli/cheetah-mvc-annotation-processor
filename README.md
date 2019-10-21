# cheetah-mvc-annotation-processor
Annotation processor for creating Controller-Service-Repository classes

Create your entities.

Create an interface for each entity and annotate it with CheetahSpring annotation. 

Specify mandatory attributes (entity qualified name, httprest path and pkgroot).

Add this dependency to the compiler plugin 

<dependency>
    <groupId>org.cheetah.spring</groupId>
    <artifactId>processor</artifactId>
    <version>1.0.6</version>
</dependency>
<dependency>
    <groupId>org.cheetah.spring</groupId>
    <artifactId>annotation</artifactId>
    <version>1.0.6</version>
</dependency>

And add the annotation processor to the compiler plugin

<annotationProcessors>
    <annotationProcessor>org.cheetah.processor.CheetahProcessor</annotationProcessor>
</annotationProcessors>

That's all.


