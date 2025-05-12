package com.app.digital_store.config;

import com.app.digital_store.entity.Product;
import com.app.digital_store.entity.ProductType;
import com.app.digital_store.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductSeeder {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void seedProducts() {
        if (productRepository.count() == 0) {
            List<Product> products = List.of(
                    new Product(null, "Java Basics eBook", ProductType.EBOOK,
                            "/images/java_ebook.png", "/downloads/ebook/java_ebook.pdf", 799.0),

                    new Product(null, "Chronicles of Narnia Piano Theme", ProductType.MUSIC,
                            "/images/music.jpg", "/downloads/music/The Chronicles of Narnia Theme Piano.mp3", 199.0),

                    new Product(null, "RDJ Aesthetic", ProductType.PHOTOGRAPHY,
                            "/images/photo_pack.jpg", "/downloads/photos/rdj.jpg", 299.0),

                    new Product(null, "Code Editor", ProductType.SOFTWARE,
                            "/images/editor.png", "/downloads/software/npp.8.7.7.Installer.x64.exe", 2599.0),

                    new Product(null, "C++ Programming", ProductType.EBOOK,
                            "/images/cpp_ebook.png", "/downloads/ebook/cpp_ebook.pdf", 999.0),

                    new Product(null, "Harry Potter and the Sorcerer's Stone", ProductType.EBOOK,
                            "/images/harryPotter1_ebook.png", "/downloads/ebook/harryPotter1_ebook.pdf", 599.0),

                    new Product(null, "Sherlock Holmes Manes Mystery", ProductType.EBOOK,
                            "/images/sherlockHolmes_ebook.png", "/downloads/ebook/sherlockHolmes_ebook.pdf", 599.0),

                    new Product(null, "Narnia The Lion, Witch and Wardrobe", ProductType.EBOOK,
                            "/images/narnia1_ebook.png", "/downloads/ebook/narnia1_ebook.pdf", 599.0),

                    new Product(null, "SpiderMan Origin", ProductType.EBOOK,
                            "/images/spidermanOrigin_ebook.png","/downloads/ebook/spidermanOrigin_ebook.pdf",159.0),

                    new Product(null, "Linux Basics", ProductType.EBOOK,
                            "/images/linuxBasics_ebook.png","/downloads/ebook/linuxBasics_ebook.pdf",159.0),

                    new Product(null, "Assembly for x86 Architecture", ProductType.EBOOK,
                            "/images/x86Assembly_ebook.png","/downloads/ebook/x86Assembly_ebook.pdf",159.0),

                    new Product(null,"Sunflower from SpiderVerse",ProductType.MUSIC,
                            "/images/spiderman_sunflower.jpg","/downloads/music/spiderman_sunflower.mp3",299.9),

                    new Product(null, "Bye Bye Bye",ProductType.MUSIC,
                            "/images/deadpool_3.jpg","/downloads/music/bye_bye_bye.mp3",999.0),

                    new Product(null,"JurassicPark Theme",ProductType.MUSIC,
                            "/images/jurassicPark_music.jpeg","/downloads/music/jurassic_park_theme.mp3",499),

                    new Product(null,"Batman Song",ProductType.MUSIC,
                            "/images/batman_music.jpeg","/downloads/music/batman_song.mp3",597.65),

                    new Product(null,"Interstellar Cornfield Theme",ProductType.MUSIC,
                            "/images/interstellar_music.jpeg","/downloads/music/interstellar_theme.mp3",1499.0),

                    new Product(null,"Naa Ready",ProductType.MUSIC,
                            "/images/naaReady_music.jpeg","/downloads/music/naa_ready.mp3",799.0),

                    new Product(null,"Pirates of Carribean",ProductType.MUSIC,
                            "/images/potc_music.jpg","/downloads/music/pirates_theme.mp3",1599.0),

                    new Product(null,"Ratata from Leo",ProductType.MUSIC,
                            "/images/ratata_music.jpeg","/downloads/music/ratata_leo.mp3",599.0)
            );
            productRepository.saveAll(products);

        }

    }
}
