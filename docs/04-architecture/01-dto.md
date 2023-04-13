# Data transfer objects


## Match

Представляет матч между двумя игроками

1. matchid 				 Идентификатор встреи (UUID) - primary key
2. eventid				 Идентификатор события (UUID)
3. particapant1			 Фамилия первого участника (text)
4. particapant2			 Фамилия второго участника (text)
5. score1				 Очки (геймы) первого участника (integer)
6. score2				 Очки (геймы) второго участника (integer)
7. oourt				 наименование корта 	(text) 
7. datetime 			 время начала матча (timestamp)
8. statusid				 идентификатор статуса встречи (UUID) -> MatchStatus

## MatchStatus

Справочник представляет статус встречи

1. statusid идентификатор статуса встречи (UUID)
2. code		код статуса (text)
3. name		аименование статуса (text)

## TODO

За рамками выпускного проекта остается представление событий, таблица представления учасников встречи
