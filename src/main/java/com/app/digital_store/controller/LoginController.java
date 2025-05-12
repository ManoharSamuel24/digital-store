
package com.app.digital_store.controller;

import com.app.digital_store.config.AdminSessionManager;
import com.app.digital_store.entity.Product;
import com.app.digital_store.entity.ProductType;
import com.app.digital_store.entity.User;
import com.app.digital_store.repository.ProductRepository;
import com.app.digital_store.repository.UserRepository;
import com.app.digital_store.service.ProductService;
import com.app.digital_store.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {

        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("userId", user.get().getId());
            model.addAttribute("username", user.get().getUsername());

            addProductCategories(model);
            return "welcome";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String phone,
                               @RequestParam String account,
                               @RequestParam String bank,
                               @RequestParam String pin,
                               Model model, HttpSession session) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setAccountNumber(account);
        user.setBankName(bank);
        user.setPin(pin);
        userService.save(user);

        session.setAttribute("userId", user.getId());
        model.addAttribute("username", username);
        model.addAttribute("userId", user.getId());

        addProductCategories(model);

        return "welcome";
    }


    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null || userId == 0) {
            return "redirect:/";
        }

        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("username", user.getUsername());
        }

        List<Product> products = productService.getAllProducts();
        model.addAttribute("ebooks", filterProductsByType(products, "ebook"));
        model.addAttribute("music", filterProductsByType(products, "music"));
        model.addAttribute("photography", filterProductsByType(products, "photography"));
        model.addAttribute("software", filterProductsByType(products, "software"));

        return "welcome";
    }



    private void addProductCategories(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("ebooks", filterProductsByType(products, "ebook"));
        model.addAttribute("music", filterProductsByType(products, "music"));
        model.addAttribute("photography", filterProductsByType(products, "photography"));
        model.addAttribute("software", filterProductsByType(products, "software"));
    }

    private List<Product> filterProductsByType(List<Product> products, String type) {
        return products.stream()
                .filter(p -> p.getProductType().name().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloads/{id}")
    public String downloadPage(@PathVariable int id, Model model) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            return "downloadPage";
        }
        return "redirect:/";
    }

    @GetMapping("/buy/{id}")
    public String buyProductPage(@PathVariable int id, Model model, HttpSession session) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);

            int userId = (int) session.getAttribute("userId");
            Optional<User> userOptional = userService.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("userBankName", user.getBankName());
            }
            return "buyProduct";
        }
        return "redirect:/";
    }

    @PostMapping("/buy/{id}")
    public String buyProduct(@PathVariable Long id,
                             @RequestParam("pin") String pin,
                             HttpSession session,
                             Model model) {

        Long userId = Long.valueOf(session.getAttribute("userId").toString());
        if (null == userId) {
            return "redirect:/login";
        }

        Optional<User> optionalUser = userRepository.findById(Math.toIntExact(userId));
        Optional<Product> optionalProduct = productRepository.findById(Math.toIntExact(id));

        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            User user = optionalUser.get();
            Product product = optionalProduct.get();

            if (!user.getPin().equals(pin)) {
                model.addAttribute("product", product);
                model.addAttribute("userBankName", user.getBankName());
                model.addAttribute("error", "Invalid PIN. Please try again.");
                return "buyProduct";
            }

            // Correct PIN - increase download count
            if(user.getDownloads()==null){
                user.setDownloads(0);
            }
            user.setDownloads(user.getDownloads() + 1);
            userRepository.save(user);

            model.addAttribute("product", product);
            model.addAttribute("message", "Payment successful! You can now download the product.");
            return "paymentSuccess"; // or any success page you want
        }

        return "redirect:/welcome";
    }





    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int id) throws MalformedURLException {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            String url = product.getProductDownloadPath();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", url);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/adminLogin")
    public String showAdminLoginPage() {
        return "adminLogin";
    }

    @PostMapping("/adminLogin")
    public String handleAdminLogin(@RequestParam String adminPassword, HttpSession session, Model model) {
        if ("admin1234".equals(adminPassword)) {
            session.setAttribute("isAdmin", true);
            AdminSessionManager.markAdminLoggedIn(session.getId());
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Invalid admin password");
            return "adminLogin";
        }
    }


    @GetMapping("/admin")
    public String showAdminDashboard(Model model, HttpSession session) {
        if (!AdminSessionManager.isAdmin(session.getId())) {
            return "redirect:/adminLogin";
        }


        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String addProduct(@RequestParam("productName") String productName,
                             @RequestParam("productType") String productType,
                             @RequestParam("productPrice") double productPrice,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @RequestParam("productFile") MultipartFile productFile,
                             HttpServletRequest request,
                             HttpSession session) {

        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            return "redirect:/adminLogin";
        }

        try {
            String uploadDir = request.getServletContext().getRealPath("/");

            // Save image file
            String imageFileName = imageFile.getOriginalFilename();
            String imagePath = "images/" + imageFileName;
            File imageDest = new File(uploadDir + imagePath);
            imageDest.getParentFile().mkdirs();
            imageFile.transferTo(imageDest);

            // Save product file
            String originalProductFileName = productFile.getOriginalFilename();
            String ext = originalProductFileName.substring(originalProductFileName.lastIndexOf('.'));
            String safeName = productName.trim().replaceAll("\\s+", "_");
            String downloadPath = "downloads/" + safeName + ext;
            File downloadDest = new File(uploadDir + downloadPath);
            downloadDest.getParentFile().mkdirs();
            productFile.transferTo(downloadDest);

            Product product = new Product(null,
                    productName,
                    ProductType.valueOf(productType),
                    "/" + imagePath,
                    "/" + downloadPath,
                    productPrice);

            productService.saveProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }



    @PostMapping("/admin/remove")
    public String removeProduct(@RequestParam int id) {
        productService.deleteProductById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/product")
    public String getProductById(@RequestParam int id, Model model, HttpSession session) {
        if (session.getAttribute("isAdmin") == null) return "redirect:/adminLogin";

        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("users", userService.getAllUsers());
        productService.findById(id).ifPresent(p -> model.addAttribute("productById", p));
        return "admin";
    }

    @GetMapping("/admin/user")
    public String getUserById(@RequestParam int id, Model model, HttpSession session) {
        if (session.getAttribute("isAdmin") == null) return "redirect:/adminLogin";

        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("users", userService.getAllUsers());
        userService.findById(id).ifPresent(u -> model.addAttribute("userById", u));
        return "admin";
    }

}
