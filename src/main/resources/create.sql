CREATE table sensor (
    id int primary key generated by default as identity,
    name varchar(100)
);
CREATE Table measurements(
    id int primary key generated by default as identity,
    value real,
    raining boolean,
    sensor_id int references sensor(id)
)