package com.example.aiplantscanner

object PlantDatabase {

    private val plants = listOf(

        PlantInfo(
            label = "0 Healthy Calamansi",
            commonName = "Calamansi",
            scientificName = "Citrus × microcarpa",
            condition = "Healthy",
            description = """
        Calamansi is a small, round citrus fruit native to Southeast Asia, prized for its sour, tangy flavor and fragrant aroma. 
        The fruit is bright green when unripe and turns orange when fully mature. Leaves are glossy and dark green, with a slightly leathery texture. 
        Traditionally used in drinks, marinades, sauces, and desserts, it is a staple in Filipino cuisine. 
        Calamansi thrives in well-drained loamy soil, needs full sun, moderate watering, and temperatures between 20–30°C. 
        Fun fact: it’s sometimes called the “Philippine lime” and is also used for ornamental purposes in home gardens.
    """.trimIndent(),
            treatment = "No treatment needed. Maintain regular watering and sunlight."
        ),

        PlantInfo(
            label = "1 Citrus Canker",
            commonName = "Calamansi",
            scientificName = "Citrus × microcarpa",
            condition = "Infected - Citrus Canker",
            description = """
        Citrus canker is a bacterial disease caused by Xanthomonas axonopodis. 
        It produces circular, corky lesions on leaves, stems, and fruit, often surrounded by yellow halos. 
        Originating from Southeast Asia, it spreads rapidly through wind, rain, and contaminated tools. 
        Fruits from infected trees are generally safe to eat if washed, but may be misshapen or blemished. 
        Prevention includes proper sanitation, avoiding overhead watering, and planting resistant varieties.
    """.trimIndent(),
            treatment = """
        Remove infected leaves, disinfect pruning tools, and apply copper-based fungicides. 
        Avoid overhead watering to reduce spread.
    """.trimIndent()
        ),

        PlantInfo(
            label = "0 Healthy Ampalaya",
            commonName = "Ampalaya",
            scientificName = "Momordica charantia",
            condition = "Healthy",
            description = """
        Ampalaya, or bitter melon, is a tropical vine producing wrinkled, green fruits known for their bitter taste. 
        Leaves are deeply lobed, bright green, and have a slightly rough texture. 
        Used extensively in Southeast Asian cuisine and traditional medicine. 
        Thrives in warm, sunny locations with fertile, well-draining soil. Requires moderate watering and support for climbing vines. 
        Cool fact: its leaves and fruits are rich in vitamins and may help regulate blood sugar.
    """.trimIndent(),
            treatment = "No treatment needed. Ensure proper sunlight and moderate watering."
        ),

        PlantInfo(
            label = "1 Downey Mildey",
            commonName = "Ampalaya",
            scientificName = "Momordica charantia",
            condition = "Infected - Downy Mildew",
            description = """
        Downy mildew is a fungal-like disease caused by Pseudoperonospora cubensis. 
        Symptoms include yellow to brown patches on leaf surfaces, with grayish fuzz underneath. 
        Originally found in tropical and subtropical regions, it spreads via water, humidity, and wind. 
        Infected leaves should not be consumed. Avoid overcrowding plants and maintain good airflow.
    """.trimIndent(),
            treatment = """
        Remove affected leaves, improve air circulation, and apply recommended fungicides weekly as a preventive measure.
    """.trimIndent()
        ),

        PlantInfo(
            label = "0 Healthy Sitaw",
            commonName = "Sitaw",
            scientificName = "Vigna unguiculata subsp. sesquipedalis",
            condition = "Healthy",
            description = """
        Sitaw, or yardlong bean, is a climbing legume producing long, slender green pods. 
        Leaves are trifoliate, smooth, and bright green. Flowers are purple or white. 
        Widely used in Asian cuisines, stir-fries, and soups. 
        Prefers fertile, well-draining soil, full sunlight, and consistent watering. Needs trellis support for optimal growth.
    """.trimIndent(),
            treatment = "No treatment needed. Maintain adequate sunlight and soil moisture."
        ),

        PlantInfo(
            label = "1 Bean Rust",
            commonName = "Sitaw",
            scientificName = "Vigna unguiculata subsp. sesquipedalis",
            condition = "Infected - Bean Rust",
            description = """
        Bean rust is caused by the fungus Uromyces appendiculatus. 
        Symptoms include reddish-brown pustules on the leaves, leading to premature leaf drop. 
        Originating in Africa, it spreads via wind and water. Infected pods and leaves are not recommended for consumption. 
        Proper spacing, resistant varieties, and avoiding overhead irrigation can reduce infection risk.
    """.trimIndent(),
            treatment = """
        Remove infected leaves, avoid wetting foliage, and apply fungicides as recommended.
    """.trimIndent()
        ),

        PlantInfo(
            label = "0 Healthy Gabi",
            commonName = "Gabi",
            scientificName = "Colocasia esculenta",
            condition = "Healthy",
            description = """
        Gabi, or taro, is a tropical plant cultivated for its starchy corms and edible leaves. 
        Leaves are large, heart-shaped, and deep green with a smooth texture. 
        Common in Southeast Asian and Pacific cuisines. Requires rich, moist soil, partial to full sunlight, and consistent watering. 
        Fun fact: taro is often grown in flooded conditions to mimic its natural habitat.
    """.trimIndent(),
            treatment = "No treatment needed. Keep soil moist and provide sufficient sunlight."
        ),

        PlantInfo(
            label = "1 Taro Leaf Blight",
            commonName = "Gabi",
            scientificName = "Colocasia esculenta",
            condition = "Infected - Taro Leaf Blight",
            description = """
        Taro leaf blight is a fungal disease caused by Phytophthora colocasiae. 
        Brown, water-soaked lesions appear on leaves and expand rapidly, leading to defoliation. 
        Originates from Southeast Asia and spreads in wet, humid conditions. 
        Infected leaves should not be eaten. Crop rotation and resistant varieties help prevent outbreaks.
    """.trimIndent(),
            treatment = """
        Remove infected leaves, improve airflow, and apply fungicides as needed.
    """.trimIndent()
        ),

        PlantInfo(
            label = "0 Healthy Talong",
            commonName = "Talong",
            scientificName = "Solanum melongena",
            condition = "Healthy",
            description = """
        Talong, or eggplant, is a warm-season vegetable with glossy green leaves and smooth purple fruits. 
        Leaves are ovate and slightly fuzzy. Used widely in stir-fries, stews, and grilled dishes. 
        Thrives in well-draining soil, full sunlight, moderate watering, and temperatures between 18–30°C. 
        Fun fact: eggplant was first domesticated in India and has many heirloom varieties.
    """.trimIndent(),
            treatment = "No treatment needed. Maintain proper watering and sunlight."
        ),

        PlantInfo(
            label = "1 Bacterial Wilt",
            commonName = "Talong",
            scientificName = "Solanum melongena",
            condition = "Infected - Bacterial Wilt",
            description = """
        Bacterial wilt, caused by Ralstonia solanacearum, leads to rapid wilting and collapse of stems and leaves. 
        The pathogen spreads through soil and contaminated water. 
        Infected fruits and leaves are unsafe to eat. Rotation and removal of infected plants are key preventive measures.
    """.trimIndent(),
            treatment = """
        Remove infected plants, avoid soil contamination, and rotate crops to prevent recurrence.
    """.trimIndent()
        )
    )

    fun getPlant(label: String): PlantInfo? {
        return plants.find { it.label == label }
    }
}