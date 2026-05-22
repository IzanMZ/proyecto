-- 1
select b.nombre,b.lugar_origen, a.titulo as titulos_albumes,
count(ca.codigo_album) as numero_canciones
from banda b 
join album a on b.codigo = a.codigo_banda 
left join cancion ca on ca.codigo_album = a.codigo
group by b.nombre, b.lugar_origen, titulos_albumes
order by b.lugar_origen asc;

-- 2
select mu.nombre,mu.instrumento,a.titulo as titulo_album,
count(ca.codigo_album) as canciones_en_album
from musico mu 
join album a on mu.codigo = a.codigo_musico
join cancion ca on ca.codigo_album = a.codigo
group by mu.nombre,mu.instrumento,a.titulo
order by instrumento asc;

-- 3
select a.anio_publicacion, a.titulo as titulo_album, ca.titulo as titulo_cancion
from album a
join cancion ca on ca.codigo_album = a.codigo
order by a.anio_publicacion asc;

-- 4
select ca.compositor,ca.titulo as titulo_cancion,a.titulo as titulo_album
from cancion ca
join album a on ca.codigo_album = a.codigo
group by ca.compositor,titulo_cancion,titulo_album
order by ca.compositor asc;

-- 5
select b.nombre, count(a.codigo) as albumes_publicados
from banda b
join album a on b.codigo = a.codigo_banda
group by b.nombre
order by albumes_publicados desc;

-- 6
select a.titulo as titulo_album,
count(ca.codigo) as canciones_en_album,
coalesce(mu.nombre, b.nombre) as autor,
case 
    when mu.codigo is not null then 'Musico'
    when b.codigo is not null then 'Banda'
end as banda_o_musico
from album a
left join banda b on b.codigo = a.codigo_banda
left join musico mu on a.codigo_musico = mu.codigo
join cancion ca on ca.codigo_album = a.codigo
group by a.codigo,a.titulo,mu.nombre,b.nombre,mu.codigo,b.codigo
having count(ca.codigo_album) > 12;

-- 7
select b.nombre as nombre_banda
from banda b
left join musico mu on b.codigo = mu.codigo_banda
where mu.codigo is null;

-- 8
select a.titulo AS titulo_album,
round(AVG(CAST(ca.duracion AS UNSIGNED)),2) as duracion_media
from album a
join cancion ca on ca.codigo_album = a.codigo
group by a.titulo;

-- 9
select nombre, nacionalidad, instrumento
from musico
where nacionalidad = 'Española'
and instrumento like '%Guitarra%';






















