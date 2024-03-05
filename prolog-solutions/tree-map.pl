% node(K, V, L, R)

find_top([(K, V) | T], Ind, Cur, H, Key, Value, Head, Tail) :-
	Cur >= Ind,
	Key = K,
	Value = V,
	Tail = T,
	Head = H, !.

find_top([A | T], Ind, Cur, H, Key, Value, Head, Tail) :-
	NextCur is Cur + 1,
	find_top(T, Ind, NextCur, [A | H], Key, Value, Head, Tail).

map_build([], null) :- !.
map_build(MapList, TreeMap) :- 
	length(MapList, Len),
	TopInd is Len / 2 - 1,
	find_top(MapList, TopInd, 0, [], Key, Val, H, T),
	reverse(H, GH),
	map_build(GH, L),
	map_build(T, R),
	TreeMap = node(Key, Val, L, R).
	

map_get(node(K, V, _, _), K, V) :- !.
map_get(node(K, _, L, R), Key, V) :- Key < K, map_get(L, Key, V), !.
map_get(node(K, _, L, R), Key, V) :- map_get(R, Key, V), !.

map_minKey(node(K, _, L, _), Key) :-
	L = null, 
	Key = K, !.

map_minKey(node(_, _, L, _), Key) :-
	map_minKey(L, Key).

map_maxKey(node(K, _, _, R), Key) :-
	R = null, 
	Key = K, !.

map_maxKey(node(_, _, _, R), Key) :-
	map_maxKey(R, Key).

