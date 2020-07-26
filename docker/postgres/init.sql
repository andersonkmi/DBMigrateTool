--- Create all sequences ---
CREATE SEQUENCE accountseq;
CREATE SEQUENCE accountuserseq;

--- Create all tables ---
CREATE TABLE account (
        accountid bigint not null primary key DEFAULT NEXTVAL('accountseq'),
        name varchar(80) not null unique,
        email varchar (80) not null unique,
        accesskey varchar(120) not null unique,
        secretkey varchar(120) not null unique,
        creationDate timestamp with time zone not null,
        accountstatus varchar(20) not null
);

CREATE TABLE accountuser (
    accountuserid bigint not null primary key default nextval ('accountuserseq'),
    accountid bigint not null,
    firstname varchar (30) not null,
    lastname varchar (30) not null,
    email varchar (40) not null,
    password varchar (256) not null,
    accesskey varchar(120) not null,
    secretkey varchar(120) not null,
    creationdate timestamp with time zone not null,
    lastmodificationdate timestamp with time zone not null,
    isactive boolean not null,
    isadmin boolean not null,
    unique(accountid, email)
);

create table account_iprange (
        accountid bigint not null,
        ipaddress varchar(20) not null,
        unique(accountid, ipaddress)
);

--- Make all the necessary alters ---
ALTER TABLE accountuser ADD CONSTRAINT accountuser_account FOREIGN KEY (accountid) REFERENCES account (accountid);
ALTER TABLE account_iprange ADD CONSTRAINT iprange_account FOREIGN KEY (accountid) REFERENCES account (accountid);
