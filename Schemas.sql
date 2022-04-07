create table borrowedwalletdb(
   userId int not null,
   assetName varchar(50) not null,
   assetCoins float not null,
   interest float not null,
   primary key(userId,assetName),
   foreign key(userId) references userdb(userId)
);

create table marginwalletdb(
    userId int not null,
	assetName varchar(50) not null,
	assetCoins float not null,
	primary key(userId,assetName),
	foreign key(userId) references userdb(userId)
);

CREATE table userdb(
    userId int not null identity(1,1),
	firstName varchar(25) not null,
	middleName varchar(25) null,
	lastName varchar(25) not null,
	userName varchar(25) not null,
	email varchar(50) not null,
	password varchar(MAX) not null,
	phoneNo varchar(10) not null,
	pancardNo varchar(10) not null,
	dob date not null,
	nationality varchar(10) not null,
    marginRatio float null default 999,
    primary key(userId),
    CONSTRAINT unique_user_constraint UNIQUE (phoneNo, pancardNo, email)
);

create table orders(
 orderId int not null identity(1,1),
 userId int not null,
 orderPair varchar(20) not null,
 amount float not null,
 limitPrice float null,
 average float not null,
 orderType varchar(20) not null,
 side varchar(20) not null,
 orderStatus varchar(20) not null,
 filled float not null,
 total float not null,
 triggerPrice float null,
 timestamp datetime null,
 tradingType varchar(10) not null,
 primary key(orderId)
);

CREATE TABLE pairs(
    pair_id INT  PRIMARY key,
    symbol varchar(20) not null UNIQUE,
    asset1 VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES assets(symbol),
    asset2 VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES assets(symbol)
);

INSERT into pairs VALUES(1, 'btcusd', 'btc', 'usd');
INSERT into pairs VALUES(2, 'btcusdt', 'btc', 'usdt');
INSERT into pairs VALUES(3, 'btceur', 'btc', 'eur');
INSERT into pairs VALUES(4, 'ethusdt', 'eth', 'usdt');
INSERT into pairs VALUES(5, 'btccad', 'btc', 'cad');
INSERT into pairs VALUES(6, 'usdusdt', 'usd', 'usdt');
INSERT into pairs VALUES(7, 'usdtusdt', 'usdt', 'usdt');
INSERT into pairs VALUES(8, 'bnbusdt', 'bnb', 'usdt');
INSERT into pairs VALUES(9, 'eurusdt', 'eur', 'usdt');
INSERT into pairs VALUES(10, 'cadusdt', 'cad', 'usdt');
INSERT into pairs VALUES(11, 'inrusdt', 'inr', 'usdt');
INSERT into pairs VALUES(12, 'dogeusdt', 'doge', 'usdt');
INSERT into pairs VALUES(13, 'usdcusdt', 'usdc', 'usdt');

CREATE TABLE assets(
    asset_id INT  PRIMARY key,
    symbol varchar(20) not null UNIQUE,
    name varchar(50) not null,
    fiat int not null
);



insert into assets VALUES(1, 'btc', 'bitcoin', 0);
insert into assets VALUES(2, 'eth', 'ethereum', 0);
insert into assets VALUES(3, 'usdt', 'tether', 0);
insert into assets VALUES(4, 'bnb', 'binance coin', 0);
insert into assets VALUES(5, 'usdc', 'USD coin', 0);
insert into assets VALUES(6, 'usd', 'United States Dollar', 1);
insert into assets VALUES(7, 'inr', 'Indian Rupee', 1);
insert into assets VALUES(8, 'eur', 'euro', 1);
insert into assets VALUES(9, 'cad', 'Canadian Dollar', 1);
insert into assets VALUES(10, 'doge', 'DogeCoin', 0);

Create Table asset_market(
    id int PRIMARY key,
    asset_id int FOREIGN KEY REFERENCES assets(asset_id), 
    exchange varchar(50),
    pair varchar(50),
    type varchar(20) not null check (type IN('quote', 'base'))
);

insert into asset_market VALUES(1, 1, 'coinbase-pro', 'btcusd', 'base');
insert into asset_market VALUES(2, 1, 'coinbase-pro', 'btceur', 'base');
insert into asset_market VALUES(3, 1, 'coinbase-pro', 'ethbtc', 'quote');

create table spotwalletdb(
   userId int not null,
   assetName varchar(50) not null,
   assetCoins int not null,
   primary key(userId,assetName),
   foreign key(userId) references userdb(userId)
);

create table trades ( 
tradeId int primary key Identity(1,1),
filled float,
total float,
price float,
buy_orderId int,
sell_orderId int,
timestamp datetime
);