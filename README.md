This is second version of StarWars application for EasyCode..

All my thoughts in code were marked in code by TODO. They can be strongly stupid :-)

structure
-data
-domain
-presentation

I think i need to:
-may add a pagination(now if we start search with clear query, then we will get a first page of characters(i didn't notice that we get a few pages of characters)
-may change flow to sharedFlow? and how to implement use case logic more correctly
should i use more sealed class? more uiState classes?
-may remove stupid classes like CharacterId, CharacterNameParam when we have one param
-check analyze a code state
-rename stupid variable
-when user delete character from favorite don`t delete immediately only after snackbar is went
-add DiffUtils to Recycler

