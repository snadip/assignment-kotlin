DROP TABLE IF EXISTS MOVIE_CAST;
DROP TABLE IF EXISTS MOVIE;
DROP TABLE IF EXISTS ACTOR;
  
CREATE TABLE MOVIE (
  movie_id INT AUTO_INCREMENT  PRIMARY KEY,
  movie_name VARCHAR(250) NOT NULL,
  movie_release_dt DATE NOT NULL,
  last_updt_by VARCHAR(250) DEFAULT 'APP_USER',
  last_updt_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ACTOR(
  actor_id INT AUTO_INCREMENT  PRIMARY KEY,
  actor_name VARCHAR(250) NOT NULL,
  last_updt_by VARCHAR(250) DEFAULT 'APP_USER',
  last_updt_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE MOVIE_CAST(
  movie_id INT NOT NULL,
  actor_id INT NOT NULL,
  last_updt_by VARCHAR(250) DEFAULT 'APP_USER',
  last_updt_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (movie_id) references MOVIE(movie_id),
  FOREIGN KEY (actor_id) references ACTOR(actor_id)
);