/* DELETE 'musicfreeDB' database*/
DROP SCHEMA IF EXISTS musicfreeDB;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'%';

/* CREATE 'musicfreeDB' DATABASE */
CREATE SCHEMA musicfreeDB;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'%' IDENTIFIED BY 'spq';

GRANT ALL ON musicfreeDB.* TO 'spq'@'%';