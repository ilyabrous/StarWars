### Star Wars

Stack: Clear Arch, Use Cases. MVVM, Dagger Hilt, Coroutines, Flow, Jetpack Navigation, Room, Retrofit.
Api: https://swapi.dev

Три слоя архитектуры: data, domain, presentation. Переиспользование логики в Use Cases. Использование Dagger Hilt для внедрения зависимостей. База данных, которая хранит закешированных персонажей. Отношение MxN, т.к каждый персонаж может иметь много фильмов, машин и прочего, и так-же наоборот: каждый фильм имеет много актеров, например. Поэтому продуманна логика разбития на crossRefTables. Сохранение каждых данных в подходяших таблицах.

![](https://media.giphy.com/media/dkIRONffYSabFafOLn/giphy.gif)


