node(K, V, C, L, R, node(K, V, C, L, R)).

map_get(node(K, V, C, _, _), K, V) :- !.
map_get(node(K, V, C, _, R), K1, V1) :-
        K < K1,
        map_get(R, K1, V1).
map_get(node(K, V, C, L, _), K1, V1) :-
        K > K1,
        map_get(L, K1, V1).

merge(0, T, T) :- !.
merge(T, 0, T) :- !.
merge(node(K1, V1, C1, L1, R1), node(K2, V2, C2, L2, R2), node(K1, V1, C1, L1, R)) :-
        C2 < C1,
        merge(R1, node(K2, V2, C2, L2, R2), R), !.
merge(node(K1, V1, C1, L1, R1), node(K2, V2, C2, L2, R2), node(K2, V2, C2, L, R2)) :-
        merge(node(K1, V1, C1, L1, R1), L2, L), !.

split(0, _, (0, 0)) :- !.
split(node(K, V, C, L, R), X, (Res, T2)) :-
        K < X , split(R, X, (T1, T2)),
        node(K, V, C, L, T1, Res1),
        update(Res1, Res), !.
split(node(K, V, C, L, R), X, (T1, Res)) :-
        split(L, X, (T1, T2)),
        node(K, V, C, T2, R, Res1),
        update(Res1, Res).

get_c(0, 0) :- !.
get_c(node(K, V, C, L, R), C).

update(node(K, V, C, L, R), Res) :-
        get_c(L, C1),
        get_c(R, C2),
        CR is 1 + C1 + C2,
        node(K, V, CR, L, R, Res).

insert(N, 0, N) :- !.
insert(0, N, N) :- !.
insert(T, node(K, V, C, L, R), Res) :-
        split(T, K, (T1, T2)),
        merge(T1, node(K, V, C, L, R), T3),
        merge(T3, T2, Res).

map_build(L, Res) :- map_build(L, 0, Res).
map_build([], N, N) :- !.
map_build([(K, V) | T], Root, Res) :-
        insert(Root, node(K, V, 1, 0, 0), Root1),
        map_build(T, Root1, Res).

map_containsKey(T, K) :- map_get(T, K, V).

map_containsValue(node(_, V, _, _, _), V) :- !.
map_containsValue(node(K, V, C, L, R), V1) :- map_containsValue(L, V1), !.
map_containsValue(node(K, V, C, L, R), V1) :- map_containsValue(R, V1). 


map_put(T, K, V, Res) :-
        map_remove(T, K, Q),
        node(K, V, 1, 0, 0, N),
        insert(Q, N, Res).
map_remove(T, X, Res) :-
        split(T, X, (T1, T2)), X1 is X + 1,
        split(T2, X1, (T11, T12)),
        merge(T1, T12, Res).

map_getLast(node(K, V, C, L, 0), (K, V)) :- !.
map_getLast(node(_, _, C, L, R), (K1, V1)) :- map_getLast(R, (K1, V1)).
map_removeLast(0,0). 
map_removeLast(T, Res) :- map_getLast(T, (K, V)), map_remove(T, K, Res).

