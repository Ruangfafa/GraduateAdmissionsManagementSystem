FROM openjdk:17
EXPOSE 8080
ADD target/GraduateAdmissionsManagementSystem-0.0.1-SNAPSHOT.jar GraduateAdmissionsManagementSystem-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "GraduateAdmissionsManagementSystem-0.0.1-SNAPSHOT.jar"]