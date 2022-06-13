
# AGRAPANA APP

Agrapana App is an application that can classify fruit ripeness from photos. This helps you identify whether the fruits ripeness matches your desired level of ripeness. You can take a picture of your fruits or choose from your gallery and upload it, and then the apps will show the ripeness level, such as ripe, unripe, and overripe. Besides, this application will show the percentage level of accuracy of classification results.


## Team Member C22-PS243

- (ML) M2002F0117 - Prana Gusriana - Institut Teknologi Bandung [@pranagusriana](https://github.com/pranagusriana)
- (ML) M2008F0862 - Rhendiya Maulana Zein - Universitas Gadjah Mada [@RendiZein](https://github.com/RendiZein)
- (MD) A2012F1220 - Luthfi Noor Syadzy Zamachsyari - Telkom University [@Syn57](https://github.com/Syn57)
- (MD) A1234P1234 - Muhammad Ildha - President University [@Catfish-Emperor](https://github.com/Catfish-Emperor)
- (CC) C2012F1318 - Shafira Amkha Zahra - Telkom University [@ShafiraAmkha](https://github.com/ShafiraAmkha)
- (CC) C2012F1320 - Putri Nurani - Telkom University [@putrirani](https://github.com/putrirani)

## Machine Learning
### Tools
- Google Colab: to collaborate on developing the model
- Tensorflow: to build and train the model

### Workflow
1. Data Gathering (Using this data https://github.com/giovannipcarvalho/banana-ripeness-classification/tree/master/data)
2. Split Dataset (90% Training data and 10% Validation data)
3. Data pre-processing<br/>
  a. Standardize Images: resize and normalize<br/>
  b. Data Augmentation: rotate, zoom, flip images to avoid overfitting
4. Train Model (Transfer learning from MobileNetV2 and Hyperparameters Tuning using keras Tuner)
5. Evaluation (Get and visualize the training and validation loss and accuracy)

### Link Notebooks
- Build early model: https://colab.research.google.com/drive/18bj24D2qq_esZ8t-naxvRAY-tsFJJS4U?usp=sharing
- Hyperparameter tuning and get final model: https://colab.research.google.com/drive/1phli1Dwb6_Lywss0aOqFrld3DrwrDsHB
- After get SavedModel then convert it to tensorflow js format so the model can be used in Node JS

## Mobile Development
### Components
We use Android Studio IDE for the developing of Agrapana App with kotlin as the programing language. There are several libraries which is used. 
* [Material Component](https://material.io/components/)
* [RecycleView](https://developer.android.com/guide/topics/ui/layout/recyclerview)
* [CameraX](https://developer.android.com/training/camerax)
* [Retrofit](https://square.github.io/retrofit/)
* [BottomNavigation](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Circular Progress Bar](https://github.com/lopspower/CircularProgressBar)

### Workflow
#### 1. Clone The Project
```
git clone https://github.com/RendiZein/Agrapana.git
```
#### 2. Open Android Studio and klik "Open" 
#### 3. Go directory Agrapana/MobileDevelopment
#### 4. Choose "App"
#### 3. Run or Build The App
After you open the project, wait for the Gradle to finish building first. Then you can choose to build debug app by using `Run -> Run'app'`. Or you can build signed App by head to `Build -> Generate Signed Bundle/APK`.

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

![Architecture](https://user-images.githubusercontent.com/99302582/173039761-4bbcbc90-4414-413e-ae87-554cff504122.png)


