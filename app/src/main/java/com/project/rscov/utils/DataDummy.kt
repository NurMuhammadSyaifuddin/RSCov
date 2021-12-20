package com.project.rscov.utils

import com.project.rscov.model.Hospital

object DataDummy {

    fun generateDataMovie(): List<Hospital> {
        val hospitals = mutableListOf<Hospital>()

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH  DR. ZAINOEL ABIDIN",
                "KOTA BANDA ACEH, ACEH",
                "Aceh",
                "JL. TGK DAUD BEUREUEH, NO. 108 B. ACEH",
                "(0651) 34565",
                "https://d1ojs48v3n42tp.cloudfront.net/provider_location_banner/606116_4-3-2020_18-38-54.jpg"
            )
        )

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH CUT MEUTIA KAB. ACEH UTARA",
                "KOTA LHOKSEUMAWE, ACEH",
                "Aceh",
                "JL. BANDA ACEH-MEDAN KM.6 BUKET RATA LHOKSEUMAWE",
                "(0645) 46334",
                "https://cdn-2.tstatic.net/aceh/foto/bank/images/rsu-cut-meutia-aceh-utara-rujukan-pasien-corona.jpg"
            )
        )

        hospitals.add(
            Hospital(
                "RSUP SANGLAH",
                "KOTA DENPASAR, BALI",
                "Bali",
                "JL. DIPONEGORO DENPASAR BALI",
                "(0361) 227912",
                "https://i1.wp.com/sanglahhospitalbali.com/home/wp-content/uploads/2021/08/WING-AMERTA-SIANG-HARI.jpg?w=1024&ssl=1"
            )
        )

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH KAB. BULELENG",
                "BULELENG, BALI",
                "Bali",
                "JL. NGURAH RAI 30 SINGARAJA",
                "(0362) 22046",
                "https://cdn-2.tstatic.net/bali/foto/bank/images/foto-rsud-buleleng.jpg"
            )
        )

        hospitals.add(
            Hospital(
                "RS UMUM DAERAH SANJIWANI GIANYAR",
                "GIANYAR, BALI",
                "Bali",
                "JL. CIUNG WENARA NO.2 GIANYAR",
                "(0361) 943049",
                "https://d1ojs48v3n42tp.cloudfront.net/provider_location_banner/822517_3-3-2020_2-57-59.jpg"
            )
        )

        return hospitals
    }

}