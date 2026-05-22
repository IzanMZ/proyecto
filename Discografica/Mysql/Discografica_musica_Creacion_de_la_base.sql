select * from banda;

CREATE TABLE IF NOT EXISTS Banda (
codigo INT PRIMARY KEY,
            nombre VARCHAR(30) NOT NULL,
            anios_actuacion VARCHAR(50) NOT NULL,
            lugar_origen VARCHAR(100) NOT NULL,
            genero VARCHAR(30) NOT NULL);
            
CREATE TABLE IF NOT EXISTS Musico (
            codigo INT PRIMARY KEY,
            codigo_banda INT,
            nombre VARCHAR(30) NOT NULL,
            fecha_nacimiento VARCHAR(30) NOT NULL,
            lugar_residencia VARCHAR(100) NOT NULL,
            nacionalidad VARCHAR(50) NOT NULL,
            instrumento VARCHAR(100) NOT NULL,
            FOREIGN KEY (codigo_banda) REFERENCES Banda(codigo) ON DELETE SET NULL
        );
            
            CREATE TABLE IF NOT EXISTS Album (
            codigo INT PRIMARY KEY,
            autor VARCHAR(30) CHECK(autor IN ('Banda', 'Musico')),
            codigo_banda INT,
            codigo_musico INT,
            titulo VARCHAR(50) NOT NULL,
            anio_publicacion INT NOT NULL,
            tipo VARCHAR(50) CHECK(tipo IN ('estudio', 'directo','recopilatorio')),
            duracion VARCHAR(70) NOT NULL,
            FOREIGN KEY (codigo_banda) REFERENCES Banda(codigo) ON DELETE CASCADE,
            FOREIGN KEY (codigo_musico) REFERENCES Musico(codigo) ON DELETE CASCADE
        );
        
        CREATE TABLE IF NOT EXISTS Cancion (
            codigo INT PRIMARY KEY,
            codigo_album INT NOT NULL,
            posicion INT NOT NULL,
            titulo VARCHAR(50) NOT NULL,
            compositor VARCHAR(50) NOT NULL,
            duracion VARCHAR(70) NOT NULL,
            FOREIGN KEY (codigo_album) REFERENCES Album(codigo) ON DELETE CASCADE
        );


INSERT INTO banda (codigo, nombre, anios_actuacion, lugar_origen, genero)
VALUES
(1, 'Queen', '1970-1991', 'Londres, Reino Unido', 'Rock'),
(2, 'Soda Stereo', '1982-1997', 'Buenos Aires, Argentina', 'Rock'),
(3, 'Metallica', '1981-Actualidad', 'Los Angeles, Estados Unidos', 'Metal');


INSERT INTO musico
(codigo, codigo_banda, nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento)
VALUES
(1, 1, 'Freddie Mercury', '1946/09/05', 'Londres, Reino Unido', 'Británica', 'Voz'),
(2, 1, 'Brian May', '1947/07/19', 'Londres, Reino Unido', 'Británica', 'Guitarra'),

(3, 2, 'Gustavo Cerati', '1959/08/11', 'Buenos Aires, Argentina', 'Argentina', 'Guitarra y Voz'),
(4, 2, 'Zeta Bosio', '1958/10/01', 'Buenos Aires, Argentina', 'Argentina', 'Bajo'),

(5, 3, 'James Hetfield', '1963/08/03', 'California, Estados Unidos', 'Estadounidense', 'Guitarra y Voz'),
(6, 3, 'Lars Ulrich', '1963/12/26', 'California, Estados Unidos', 'Danesa', 'Batería');


INSERT INTO album
(codigo, autor, codigo_banda, codigo_musico, titulo, anio_publicacion, tipo, duracion)
VALUES

-- Álbumes de bandas
(1, 'Banda', 1, NULL, 'A Night at the Opera', 1975, 'estudio', '00:43:00'),
(2, 'Banda', 2, NULL, 'Canción Animal', 1990, 'estudio', '00:44:12'),
(3, 'Banda', 3, NULL, 'Master of Puppets', 1986, 'estudio', '00:54:47'),

-- Álbum en solitario
(4, 'Musico', NULL, 3, 'Ahí Vamos', 2006, 'estudio', '00:51:30');


INSERT INTO cancion
(codigo, codigo_album, posicion, titulo, compositor, duracion)
VALUES

-- Queen
(1, 1, 1, 'Death on Two Legs', 'Freddie Mercury', '03:43'),
(2, 1, 2, 'Bohemian Rhapsody', 'Freddie Mercury', '05:55'),

-- Soda Stereo
(3, 2, 1, 'En la Ciudad de la Furia', 'Gustavo Cerati', '05:46'),
(4, 2, 2, 'De Música Ligera', 'Gustavo Cerati', '03:32'),

-- Metallica
(5, 3, 1, 'Battery', 'James Hetfield', '05:12'),
(6, 3, 2, 'Master of Puppets', 'James Hetfield', '08:35'),

-- Gustavo Cerati solista
(7, 4, 1, 'Déjà Vu', 'Gustavo Cerati', '05:13');











