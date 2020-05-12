object Config {
    const val minSdk = 21
    const val targetSdk = 29
    const val compileSdk = 29
    const val appVersionCode = 10000
    const val appVersionName = "1.0.0"
}

object Versions {
    const val kotlin = "1.3.72"
    const val androidPlugin = "4.0.0-beta05"

    const val androidx = "1.2.0"
    const val constraint = "1.1.3"
    // TODO: the only special thing about this lib, is that it has built-in filter functionality,
    //  consider migrating to either plain adapters, or some other lib
    const val fastadapter = "4.1.2"
    const val conductor = "3.0.0-rc1"

    const val koin = "2.1.5"

    const val room = "2.1.0-alpha07"

    const val mobius = "1.2.2"

    const val rxBinding = "3.0.0-alpha2"
    const val rxKotlin = "2.3.0"
    const val rxRelay = "2.1.0"
    const val rxAndroid = "2.1.1"

    const val timber = "4.7.1"
    const val retrofit = "2.5.0"
    const val gson = "2.8.5"

    const val leakCanary = "2.0-alpha-2"

    const val mockk = "1.9.3.kotlin12"
    const val junit = "5.3.1"
    const val testRunner = "1.1.1"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.androidx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidx}-beta01"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val material = "com.google.android.material:material:1.2.0-alpha05"

    object Conductor {
        const val core = "com.bluelinelabs:conductor:${Versions.conductor}"
        const val support = "com.bluelinelabs:conductor-support:${Versions.conductor}"
        const val rx2 = "com.bluelinelabs:conductor-rxlifecycle2:${Versions.conductor}"
        const val lifecycle = "com.bluelinelabs:conductor-archlifecycle:${Versions.conductor}"
    }

    object FastAdapter {
        const val diff = "com.mikepenz:fastadapter-extensions-diff:${Versions.fastadapter}"
        const val drag = "com.mikepenz:fastadapter-extensions-drag:${Versions.fastadapter}"
        const val scroll = "com.mikepenz:fastadapter-extensions-scroll:${Versions.fastadapter}"
        const val swipe = "com.mikepenz:fastadapter-extensions-swipe:${Versions.fastadapter}"
        const val ui = "com.mikepenz:fastadapter-extensions-ui:${Versions.fastadapter}"
        const val utils = "com.mikepenz:fastadapter-extensions-utils:${Versions.fastadapter}"
    }

    object Koin {
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val viewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val test = "org.koin:koin-test:${Versions.koin}"
    }

    object RxBinding {
        const val core = "com.jakewharton.rxbinding3:rxbinding-core:${Versions.rxBinding}"
        const val appCompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:${Versions.rxBinding}"
        const val leanBack = "com.jakewharton.rxbinding3:rxbinding-leanback:${Versions.rxBinding}"
        const val recycler = "com.jakewharton.rxbinding3:rxbinding-recyclerview:${Versions.rxBinding}"
        const val viewPager = "com.jakewharton.rxbinding3:rxbinding-viewpager:${Versions.rxBinding}"
        const val material = "com.jakewharton.rxbinding3:rxbinding-material:${Versions.rxBinding}"
    }

    object Rx {
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
        const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
        const val relay = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxRelay}"
    }

    object Room {
        const val core = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val rx = "androidx.room:room-rxjava2:${Versions.room}"
        const val test = "androidx.room:room-testing:${Versions.room}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val rx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    object Mobius {
        const val core = "com.spotify.mobius:mobius-core:${Versions.mobius}"
        const val test = "com.spotify.mobius:mobius-test:${Versions.mobius}"
        const val rx = "com.spotify.mobius:mobius-rx2:${Versions.mobius}"
        const val android = "com.spotify.mobius:mobius-android:${Versions.mobius}"
        const val extras = "com.spotify.mobius:mobius-extras:${Versions.mobius}"
    }

    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.0.0-alpha01"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val leakSentry = "com.squareup.leakcanary:leaksentry:${Versions.leakCanary}"

    const val testRunner = "androidx.test:runner:${Versions.testRunner}"

    object JUnit {
        const val api = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
        const val engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"

        object Android {
            const val core = "de.mannodermaus.junit5:android-test-core:1.0.0"
            const val runner = "de.mannodermaus.junit5:android-test-runner:1.0.0"
        }
    }

    object Mockk {
        const val core = "io.mockk:mockk:${Versions.mockk}"
        const val android = "io.mockk:mockk-android:${Versions.mockk}"
    }
}
