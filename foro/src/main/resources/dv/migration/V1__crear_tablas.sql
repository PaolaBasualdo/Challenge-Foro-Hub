CREATE TABLE alumnos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    curso ENUM(
        'ANGULAR','BASES_DE_DATOS','C_SHARP','DEVOPS',
        'HTML_CSS','JAVA','JAVASCRIPT','PYTHON','REACT','SPRING_BOOT'
    ) NOT NULL,
    activo BIT,
    alumno_id BIGINT,
    CONSTRAINT fk_alumno FOREIGN KEY (alumno_id) REFERENCES alumnos(id)
);
