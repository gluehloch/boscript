insert into bo_community(bo_shortname, bo_name, bo_year, bo_user_ref, bo_season_ref)
values ('TDKB 2026/2027', 'Bundesliga', '2026/2027', 6, 40);

insert into bo_community_user(bo_community_ref, bo_user_ref)
select 40, bo_user_ref from bo_community_user cu where cu.bo_community_ref = 38;

-- Martin04 entfernen
delete from bo_community_user where id = 399;