# WeatherDemoApplication
Test application for study new skills
For proper work of application it must contains base_config.gradle file with next content:
ext {
    buildConfigDebug = [
            server_url: "https://api.weatherbit.io/v2.0/",
            api_key : 'YOUR_KEY',
            image_api_key : 'YOUR_KEY',
            image_base_url : 'https://pixabay.com/api/'
    ]
}
