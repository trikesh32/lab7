BEGIN;

CREATE TYPE fuel_type as ENUM(
    'GASOLINE',
    'KEROSENE',
    'ALCOHOL',
    'MANPOWER'
);

CREATE TYPE vehicle_type AS ENUM(
    'CAR',
    'SUBMARINE',
    'BICYCLE',
    'HOVERBOARD'
);

CREATE TABLE IF NOT EXISTS users(
    id INT PRIMARY KEY,
    name VARCHAR(40) UNIQUE NOT NULL,
    password_hash VARCHAR(40) NOT NULL,
    salt VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL CONSTRAINT not_empty_name CHECK(length(name)>0),
    x INT NOT NULL CONSTRAINT x_check CHECK (x <= 636),
    y FLOAT NOT NULL,
    creation_date DATE DEFAULT NOW() NOT NULL,
    engine_power INT NOT NULL CONSTRAINT check_power CHECK (engine_power > 0),
    capacity INT NOT NULL CONSTRAINT check_capacity CHECK (capacity > 0),
    type vehicle_type NOT NULL,
    fuel_type fuel_type,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE SEQUENCE id_sequence;
CREATE SEQUENCE user_id_sequence;

END;