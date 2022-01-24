-- ----------------------------------------------------------------------------
-- Model
-- -----------------------------------------------------------------------------

DROP TABLE Reserva;
DROP TABLE Excursion;


-- ----------------------------Excursion---------------------------------------
CREATE TABLE Excursion(idExcursion BIGINT NOT NULL AUTO_INCREMENT,
    ciudad VARCHAR(50) COLLATE latin1_bin NOT NULL,
    descripcion VARCHAR(1024) COLLATE latin1_bin NOT NULL,
    fechaExcursion DATETIME NOT NULL,
    precioPorPersona FLOAT NOT NULL,
    numeroMaximoPlazas INT NOT NULL,
    fechaAltaExcursion DATETIME NOT NULL,
    numeroPlazasDisponibles INT NOT NULL,
    CONSTRAINT ExcursionPK PRIMARY KEY(idExcursion)) ENGINE = InnoDB;


-- ----------------------------Reserva---------------------------------------
CREATE TABLE Reserva(idReserva BIGINT NOT NULL AUTO_INCREMENT,
    idExcursion BIGINT NOT NULL,
    email VARCHAR(50) COLLATE latin1_bin NOT NULL,
    numeroPersonas INT NOT NULL,
    numeroTarjetaBancaria VARCHAR(16) NOT NULL,
    fechaReserva DATETIME NOT NULL,
    precioReserva FLOAT NOT NULL,
    fechaCancelacion DATETIME,
    CONSTRAINT ReservaPK PRIMARY KEY(idReserva),
    CONSTRAINT ReservaExcursionFK FOREIGN KEY (idExcursion)
        REFERENCES Excursion(idExcursion)) ENGINE = InnoDB;


