FROM ghcr.io/jqlang/jq:latest AS jq-stage

FROM eclipse-temurin:21-jdk AS build
COPY --from=jq-stage /jq /usr/bin/jq
# Test that jq works after copying
RUN jq --version

ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Substitua 'COPY . $HOME' por estas linhas de cópia explícita:
# 1. Copia os ficheiros essenciais do Maven Wrapper
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn/

# 2. Copia o código fonte (src/) e recursos (certifique-se de que o nome 'src' é correto)
COPY src src/

# 3. Adiciona permissão de execução (se for o caso)
RUN chmod +x ./mvnw

# If you have a Vaadin Pro key, pass it as a secret...
# ...