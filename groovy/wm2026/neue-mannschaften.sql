-- Gruppe C
-- Haiti
insert into bo_team(bo_name, bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Haiti', 'Haiti',     null,    1,           null,            5820,          'HTI',        'HTI');

-- Gruppe E
-- Curacao
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Curaçao', 'Curaçao',   null,    1,           null,            7321,          'CUW',        'CUW');

-- Gruppe H
-- Kap Verde
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Kap Verde', 'Kapverdische Inseln',   null,    1, null,        6159,          'CPV',        'CPV');

-- Gruppe I
-- Irak
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Irak', 'Irak',   null,    1, null,        4911,          'IRQ',        'IRQ');

-- Norwegen
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Norwegen', 'Norwegen',   null,    1, null,        1396,          'NOR',        'NOR');

-- Gruppe J
-- Jordanien
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Norwegen', 'Norwegen',   null,    1, null,        4915,          'JOR',        'JOR');

-- Gruppe K
-- DR Kongo
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('DR Kongo', 'DR Kongo',   null,    1, null,        4991,          'COD',        'COD');

-- Usbekistan
insert into bo_team(bo_name,   bo_longname, bo_logo, bo_teamtype, bo_location_ref, bo_openligaid, bo_shortname, bo_xshortname)
             values('Usbekistan', 'Usbekistan',   null,    1, null,        4910,          'UZB',        'UZB');

-- Team Alias
INSERT INTO betoffice.bo_teamalias (bo_aliasname, bo_team_ref) VALUES('Südkorea', 39);
INSERT INTO betoffice.bo_teamalias (bo_aliasname, bo_team_ref) VALUES('Bosnien und Herzegowina', 1183);

-- Community
INSERT INTO betoffice.bo_community(bo_shortname, bo_name, bo_year, bo_user_ref, bo_season_ref)
VALUES('TDKB 2026', 'WM Nordamerika', '2026', 6, 39);

select * from bo_community;
select * from bo_community_user where bo_community_ref = 38;

INSERT INTO betoffice.bo_community(id, bo_shortname, bo_name, bo_year, bo_user_ref, bo_season_ref)
VALUES(39, 'TDKB 2026', 'WM Nordamerika', '2026', 6, 39);

insert into bo_community_user(bo_community_ref, bo_user_ref)
select 39, bo_user_ref from bo_community_user cu where cu.bo_community_ref = 38;

select * from bo_user;
delete from bo_community_user cu where cu. bo_user_ref = 17290 and bo_community_ref = 39;
insert into bo_community_user(bo_community_ref, bo_user_ref) values (39, 22);
