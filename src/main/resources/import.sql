insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('a786246a29ac4e9d85a864081c2817a8','Fund Transfer','transfer','main_menu','2',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('8787b34a23654286a202592820dd71e9','Review Transaction','history','main_menu','3',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('ff8e1d9ed2cc4e64a2b5553ea0c8f033','Confirm Transaction','confirm_menu','transaction','1',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('0af9525662424159bbb22a69d130c4b9','Cancel Transaction','main_menu','transaction','1',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('bd2a178b82444fb88be9514c10aaeaee','Transaction','transaction','transaction','1',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('6bae670323a74f679c20ab3e80c277cc','Exit','logout','main_menu','4',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('1e81e027bc07487fb17ace4a5ff7da71','Back','main','withdraw_menu','5',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('ab764255dd334ec7b427f7a8ccb42992','$10','withdraw?amount=10','withdraw_menu','1',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('0d126265d70f4536beca7a129816e409','$50','withdraw?amount=50','withdraw_menu','2',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('095be55c48144c42a264ea2deced087e','$100','withdraw?amount=100','withdraw_menu','3',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('507159e4592a492280d1b3f8a1be4898','Other','withdraw?amount=other','withdraw_menu','4',true);
insert into public.menu(menu_id, description, route, screen_owner, sequence, status) values('68fc8af7034d4634a1f0c80526d03e17','Withdraw','withdraw?amount=','main_menu','1',true);


insert into public.account(account_id, account_no, balance, name, pin, status) values ('68fc8af7034d4634a1f0c80526d0ze17', '112233', 5000, 'John Doe', '121212', true);
insert into public.account(account_id, account_no, balance, name, pin, status) values ('68fc8af70b4d4634a1f0c80526d0ze17', '112244', 5000, 'John Doe', '111111', true);