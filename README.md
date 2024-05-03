# real_estate_website
# Tổng quan về hệ thống
Mục đích của đồ án là xây dựng một hệ thống website hỗ trợ buôn bán bất động sản
# Hệ thống này sử dụng một số công nghệ: 
- Spring boot,JWT( JSON Web token), oauth2ResourceServer, Thymeleaf Extras Security, Xampp,… 
- Kiến trúc Spring MVC,  Mapstruct with Lombok, DTO pattern
- Test API: PostMan
# Các bước để vận hành project
- Sử dụng hệ cơ sở dữ liệu MySQL vận hành database "real_easte_website".
- Vận hành chương trình với IntelliJ IDEA Community Edition 2023.3.2 theo Maven.
- Build project ban đầu thông qua "mvn clean"
- Xác định "Run Configuration" chạy project
- Trước khi chạy project, khởi tạo database tại các hệ quản trị cơ sở dữ liệu như Laragon, XAMPP, MySQL Workbench,.
- "Run" project thực hiện tạo các bảng dữ liệu cho database dựa vào các "Entity" trong project
- Chạy và trải nghiệm chức năng của website
# Một số chức năng chính
- Tài khoản ban đầu hỗ trợ đăng nhập: Tài khoản: admin, Mật khẩu: 123456789
- Đăng nhập, đăng ký, quên mật khẩu
- Hiển thị các bài viết, tạo liên hệ với chủ bài viết
- Tạo giao dịch với bài viết
- Thanh toán phí đăng bài viết
# sell_book
