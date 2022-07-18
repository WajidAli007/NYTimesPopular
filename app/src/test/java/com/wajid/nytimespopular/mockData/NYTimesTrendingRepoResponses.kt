package com.wajid.nytimespopular.mockData

object NYTimesTrendingRepoResponses {


    /**
     * helper method for getting a mocked response
     * instead of calling api every time
     */
    public fun getMockResponse(): String {
        return "{\n" +
                "  \"status\": \"OK\",\n" +
                "  \"copyright\": \"Copyright (c) 2022 The New York Times Company.  All Rights Reserved.\",\n" +
                "  \"num_results\": 20,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"uri\": \"nyt://article/f5c45f2f-ea3f-50ca-94b0-3e585c7e7ab4\",\n" +
                "      \"url\": \"https://www.nytimes.com/2022/07/14/nyregion/ivana-trump-dead.html\",\n" +
                "      \"id\": 100000008444686,\n" +
                "      \"asset_id\": 100000008444686,\n" +
                "      \"source\": \"New York Times\",\n" +
                "      \"published_date\": \"2022-07-14\",\n" +
                "      \"updated\": \"2022-07-15 19:19:27\",\n" +
                "      \"section\": \"New York\",\n" +
                "      \"subsection\": \"\",\n" +
                "      \"nytdsection\": \"new york\",\n" +
                "      \"adx_keywords\": \"Deaths (Obituaries);Books and Literature;Trump, Ivana;Trump, Donald J;Trump Organization;Trump Tower (Manhattan, NY)\",\n" +
                "      \"column\": null,\n" +
                "      \"byline\": \"By Clay Risen\",\n" +
                "      \"type\": \"Article\",\n" +
                "      \"title\": \"Ivana Trump, Ex-Wife of Donald Trump and Businesswoman, Dies at 73\",\n" +
                "      \"abstract\": \"She helped build his real estate empire, though she was better known for being one half of the quintessential 1980s power couple.\",\n" +
                "      \"des_facet\": [\n" +
                "        \"Deaths (Obituaries)\",\n" +
                "        \"Books and Literature\"\n" +
                "      ],\n" +
                "      \"org_facet\": [\n" +
                "        \"Trump Organization\",\n" +
                "        \"Trump Tower (Manhattan, NY)\"\n" +
                "      ],\n" +
                "      \"per_facet\": [\n" +
                "        \"Trump, Ivana\",\n" +
                "        \"Trump, Donald J\"\n" +
                "      ],\n" +
                "      \"geo_facet\": [],\n" +
                "      \"media\": [\n" +
                "        {\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"Ivana and Donald J. Trump in 1985. He announced her death on his social media platform.\",\n" +
                "          \"copyright\": \"Ron Galella via Getty Images\",\n" +
                "          \"approved_for_syndication\": 1,\n" +
                "          \"media-metadata\": [\n" +
                "            {\n" +
                "              \"url\": \"https://static01.nyt.com/images/2022/07/15/obituaries/15ivanatrump-A1-SUB/14Trump1-thumbStandard.jpg\",\n" +
                "              \"format\": \"Standard Thumbnail\",\n" +
                "              \"height\": 75,\n" +
                "              \"width\": 75\n" +
                "            },\n" +
                "            {\n" +
                "              \"url\": \"https://static01.nyt.com/images/2022/07/15/obituaries/15ivanatrump-A1-SUB/14Trump1-mediumThreeByTwo210.jpg\",\n" +
                "              \"format\": \"mediumThreeByTwo210\",\n" +
                "              \"height\": 140,\n" +
                "              \"width\": 210\n" +
                "            },\n" +
                "            {\n" +
                "              \"url\": \"https://static01.nyt.com/images/2022/07/15/obituaries/15ivanatrump-A1-SUB/14Trump1-mediumThreeByTwo440.jpg\",\n" +
                "              \"format\": \"mediumThreeByTwo440\",\n" +
                "              \"height\": 293,\n" +
                "              \"width\": 440\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"eta_id\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"uri\": \"nyt://article/a50e7c24-ecf4-5280-9f0d-6a82f81ca773\",\n" +
                "      \"url\": \"https://www.nytimes.com/2022/07/12/us/envigo-beagles-breeder-adoption.html\",\n" +
                "      \"id\": 100000008435249,\n" +
                "      \"asset_id\": 100000008435249,\n" +
                "      \"source\": \"New York Times\",\n" +
                "      \"published_date\": \"2022-07-12\",\n" +
                "      \"updated\": \"2022-07-13 23:44:28\",\n" +
                "      \"section\": \"U.S.\",\n" +
                "      \"subsection\": \"\",\n" +
                "      \"nytdsection\": \"u.s.\",\n" +
                "      \"adx_keywords\": \"Dogs;Workplace Hazards and Violations;Animal Abuse, Rights and Welfare;Animals;Law and Legislation;Adoptions;Kaine, Timothy M;Warner, Mark R;Humane Society of the United States;Virginia\",\n" +
                "      \"column\": null,\n" +
                "      \"byline\": \"By Jesus Jiménez and April Rubin\",\n" +
                "      \"type\": \"Article\",\n" +
                "      \"title\": \"4,000 Beagles Are Being Rescued From a Virginia Facility. Now They Need New Homes.\",\n" +
                "      \"abstract\": \"The authorities have about two months to find homes for the dogs, after they were found at a facility that had violated dozens of federal regulations.\",\n" +
                "      \"des_facet\": [\n" +
                "        \"Dogs\",\n" +
                "        \"Workplace Hazards and Violations\",\n" +
                "        \"Animal Abuse, Rights and Welfare\",\n" +
                "        \"Animals\",\n" +
                "        \"Law and Legislation\",\n" +
                "        \"Adoptions\"\n" +
                "      ],\n" +
                "      \"org_facet\": [\n" +
                "        \"Humane Society of the United States\"\n" +
                "      ],\n" +
                "      \"per_facet\": [\n" +
                "        \"Kaine, Timothy M\",\n" +
                "        \"Warner, Mark R\"\n" +
                "      ],\n" +
                "      \"geo_facet\": [\n" +
                "        \"Virginia\"\n" +
                "      ],\n" +
                "      \"media\": [\n" +
                "        {\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"More than 100 beagles arrived in a truck from a breeding facility in Cumberland, Va., to Homeward Trails Animal Rescue.\",\n" +
                "          \"copyright\": \"Sue Bell/Homeward Trails\\n\",\n" +
                "          \"approved_for_syndication\": 1,\n" +
                "          \"media-metadata\": [\n" +
                "            {\n" +
                "              \"url\": \"https://static01.nyt.com/images/2022/07/11/us/00xp-beagles-02/00xp-beagles-02-thumbStandard.jpg\",\n" +
                "              \"format\": \"Standard Thumbnail\",\n" +
                "              \"height\": 75,\n" +
                "              \"width\": 75\n" +
                "            },\n" +
                "            {\n" +
                "              \"url\": \"https://static01.nyt.com/images/2022/07/11/us/00xp-beagles-02/00xp-beagles-02-mediumThreeByTwo210.jpg\",\n" +
                "              \"format\": \"mediumThreeByTwo210\",\n" +
                "              \"height\": 140,\n" +
                "              \"width\": 210\n" +
                "            },\n" +
                "            {\n" +
                "              \"url\": \"https://static01.nyt.com/images/2022/07/11/us/00xp-beagles-02/00xp-beagles-02-mediumThreeByTwo440.jpg\",\n" +
                "              \"format\": \"mediumThreeByTwo440\",\n" +
                "              \"height\": 293,\n" +
                "              \"width\": 440\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"eta_id\": 0\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

}