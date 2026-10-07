# =========================================================
# 1단계: 빌드 (JDK로 코드를 jar 파일로 만듦)
# =========================================================
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Gradle 설정 파일만 먼저 복사해서 라이브러리 다운로드
# (코드만 바뀌었을 때 라이브러리를 다시 받지 않아 빌드가 빨라짐)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon > /dev/null 2>&1 || true

# 소스 코드 복사 후 실행 가능한 jar 빌드 (테스트는 건너뜀)
COPY src src
RUN ./gradlew bootJar --no-daemon -x test \
    && find build/libs -name "*.jar" ! -name "*-plain.jar" -exec cp {} app.jar \;

# =========================================================
# 2단계: 실행 (JRE만 있는 가벼운 이미지에서 jar 실행)
# =========================================================
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/app.jar app.jar

# 무료 서버 메모리(512MB)에 맞게 JVM이 쓸 메모리를 제한
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]