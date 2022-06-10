
# AGRAPANA APP

Agrapana App is an application that can classify fruit ripeness from photos. This helps you identify whether the fruits ripeness matches your desired level of ripeness. You can take a picture of your fruits or choose from your gallery and upload it, and then the apps will show the ripeness level, such as ripe, unripe, and overripe. Besides, this application will show the percentage level of accuracy of classification results.


## Team Member C22-PS243

- (ML) M2002F0117 - Prana Gusriana - Institut Teknologi Bandung [@pranagusriana](https://github.com/pranagusriana)
- (ML) M2008F0862 - Rhendiya Maulana Zein - Universitas Gadjah Mada [@RendiZein](https://github.com/RendiZein)
- (MD) A2012F1220 - Luthfi Noor Syadzy Zamachsyari - Telkom University [@Syn57](https://github.com/Syn57)
- (MD) A1234P1234 - Muhammad Ildha - President University [@Catfish-Emperor](https://github.com/Catfish-Emperor)
- (CC) C2012F1318 - Shafira Amkha Zahra - Telkom University [@ShafiraAmkha](https://github.com/ShafiraAmkha)
- (CC) C2012F1320 - Putri Nurani - Telkom University [@putrirani](https://github.com/putrirani)



## Cloud Computing

### Tech Stack

**Server:** Node, Express

**Database:** Google Cloud SQL

**Compute:** App Engine


### RESTFULL API 

#### Register User

```http
  POST /api/users/
```

| Request | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required** |
| `email` | `string` | **Required** |
| `password` | `string` | **Required** |




#### Login User for Authentication 

```http
  POST /api/users/login
```

| Request | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email` | `string` | **Required** |
| `password` | `string` | **Required** |

User accounts are authenticated using JSON Web Token. If the user successfully login, the system will generate a token that can be accessed by Mobile Development team.

#### Upload Image

```http
  POST /upload
```

| Key | Value    | Description                       |
| :-------- | :------- | :-------------------------------- |
| `image` | `.jpg` | **Required** |




## Environment Variables 

To run this project, you will need to add the following environment variables to your .env file

`APP_PORT`

`DB_PORT`

`DB_HOST`

`DB_USER`

`DB_PASS`

`DB_SOCKET`

`MYSQL_DB`


## Architecture
![Architecture](https://user-images.githubusercontent.com/99302582/173038374-2d83bede-006b-4e7d-8806-704959e8ef2f.png)



