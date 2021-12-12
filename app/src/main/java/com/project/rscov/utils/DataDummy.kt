package com.project.rscov.utils

import com.project.rscov.model.Hospital

object DataDummy {

    fun generateDataMovie(): List<Hospital> {
        val hospitals = mutableListOf<Hospital>()

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH  DR. ZAINOEL ABIDIN",
                "JL. TGK DAUD BEUREUEH, NO. 108 B. ACEH",
                "KOTA BANDA ACEH, ACEH",
                "(0651) 34565",
                "Aceh",
                "https://d1ojs48v3n42tp.cloudfront.net/provider_location_banner/606116_4-3-2020_18-38-54.jpg"
            )
        )

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH CUT MEUTIA KAB. ACEH UTARA",
                "JL. BANDA ACEH-MEDAN KM.6 BUKET RATA LHOKSEUMAWE",
                "KOTA LHOKSEUMAWE, ACEH",
                "(0645) 46334",
                "Aceh",
                "https://cdn-2.tstatic.net/aceh/foto/bank/images/rsu-cut-meutia-aceh-utara-rujukan-pasien-corona.jpg"
            )
        )

        hospitals.add(
            Hospital(
                "RSUP SANGLAH",
                "JL. DIPONEGORO DENPASAR BALI",
                "KOTA DENPASAR, BALI",
                "(0361) 227912",
                "Bali",
                "https://i1.wp.com/sanglahhospitalbali.com/home/wp-content/uploads/2021/08/WING-AMERTA-SIANG-HARI.jpg?w=1024&ssl=1"
            )
        )

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH KAB. BULELENG",
                "JL. NGURAH RAI 30 SINGARAJA",
                "BULELENG, BALI",
                "(0362) 22046",
                "Bali",
                "https://cdn-2.tstatic.net/bali/foto/bank/images/foto-rsud-buleleng.jpg"
            )
        )

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH SANJIWANI GIANYAR",
                "JL. CIUNG WENARA NO.2 GIANYAR",
                "GIANYAR, BALI",
                "(0361) 943049",
                "Bali",
                "https://d1ojs48v3n42tp.cloudfront.net/provider_location_banner/822517_3-3-2020_2-57-59.jpg"
            )
        )

        return hospitals
    }

}