drop table if exists `my_route`;
create table `my_route` (
  `id` varchar(32) NOT NULL,
  `protocol` varchar(16),
  `path` varchar(32),
  `dubbo_interface` varchar(64),
  `dubbo_version` varchar(16),
  `dubbo_method` varchar(32),
  `uri` varchar(128),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态路由';

insert into my_route (id, protocol, path, dubbo_interface, dubbo_version, dubbo_method, uri)
values ('route001', 'http', '/test', null, null, null, 'http://localhost:9999/http/test/v1'),
       ('route002', 'dubbo', '/dubbomethod', 'com.chenps3.dubbointerface', '1.0.0', 'dubbomethod', 'http://localhost:9999/dubbo');