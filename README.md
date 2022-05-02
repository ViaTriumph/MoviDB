# MoviDB
Movie db application. check for [roadmap](https://github.com/ViaTriumph/MoviDB/wiki/MoviDB-Roadmap)

#### Requirements

- Search for a movie
- See details about movie
- My list / My favorites
- Reminders

#### Functinal requirements

- Search for a movie
- Details of movie
- Reminders

#### Non Functional requirements

- Fav list
- Now playing
- Popular movies/tv shows
- Persistance

Based on the requirements lets draw High level diagram.

![HLD](/static/images/hld_dark.png)

**Cache**: Cache will act as SSOT (Single source of truth).  
**DI graph**: Dependency Injection graph.  
**Navigator**: Navigator module which handles navigation, backstack handling etc.
SearchUseCase: Handles search movie feature.
ReminderUseCase: Handles reminder to watch movie feature.
DetailsUseCase: Handles Movie details.
ExploreUserCase: Handles explore screen with popular and now playing movies.

Underlying architecture for application would be [Clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

![LLD](/static/images/lld_dark.png)

Design mocks

![LLD](/static/images/design_mock_dark.png)

Schema

```
{
    id: String,
    name: String,
    image_url: String,
    slug: String, // For more details about movie
    rating:Int,
    description:String,
    genres: List<String>,
    adult: Boolean,
    language: String,
    release_date: String
}
```
