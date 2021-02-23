drop table if exists `my_route`;
create table `my_route` (
  `id` varchar(32) NOT NULL,
  `protocol` varchar(16),
  `path` varchar(32),
  `service` varchar(32),
  `version` varchar(16),
  `uri` varchar(128),
  `priority` tinyint,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态路由';

insert into my_route (id, protocol, path, service, version, uri, priority)
values ('test001','http','test001',null,null,'localhost:9999/httptest001',1),
       ('test002','dubbo','test002','com.chenps3.dubbotest002','1.0.0','localhost:9999/dubbo',2);