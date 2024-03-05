<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="static/css/bootstrap.min.css" rel="stylesheet"/>
</head>

<body>
<div style="width: 1440px; height: 88px; border: #2b542c; border-style: solid">
    <div style="display: flex; ">
        <div style="padding-left: 130px">
            <img src="static/assets/logo.svg" alt="Spring Boot Logo" style="width: 96px; height: 96px;"/>
        </div>
        <div style="display: flex; align-items: center; padding-left: 111px">
            <ul style="display: flex; gap:30px">
                <li style="width: 95px; height: 24px"><a href="/"
                                                         style="font-size: 16px; font-weight: 900; line-height: 24px;">DNA
                    Test</a></li>
                <li style="width: 95px; height: 24px"><a href="/"
                                                         style="font-size: 16px; font-weight: 900; line-height: 24px;">Report</a>
                </li>
                <li style="width: 95px; height: 24px"><a href="/"
                                                         style="font-size: 16px; font-weight: 900; line-height: 24px;">54DNAtype</a>
                </li>
                <li style="width: 95px; height: 24px"><a href="/"
                                                         style="font-size: 16px; font-weight: 900; line-height: 24px;">Learning</a>
                </li>
            </ul>
        </div>
        <div style="width: 132px; height: 24px; display: flex; gap:20px; justify-content:center; justify-items: center; margin-top:35px; padding-right: 20px">
            <div style="width: 24px; height: 24px;">
                <img src="static/assets/searchIcon.svg" alt="searchIcon" style="width: 17.49px; height: 17.49px">
            </div>
            <div style="width: 24px; height: 24px;">
                <img src="static/assets/worldIcon.svg" alt="worldIcon" style="width: 20px; height: 20px">
            </div>

            <div id="user_id">
                ${localStorage.getItem('user')? JSON.parse(localStorage.getItem('user')) : 'Guest'}
                <script type="text/javascript">
                    document.getElementById('user_id').addEventListener('click', function () {
                        if (document.getElementById('user_id') !== 'Guest') {
                            // Get the user details from local storage
                            var userDetails = {};
                            fetch('/user/userId/' + JSON.parse(localStorage.getItem('user')), {
                                method: 'GET',
                                headers: {
                                    'Content-Type': 'application/json',
                                }
                            }).then(response => response.json())
                                .then(data => {
                                    if (data.status === "success") {
                                        userDetails = data.data;
                                        document.getElementById('loggedInUserId').value = userDetails.userId;
                                        document.getElementById('loggedInNickname').value = userDetails.nickName;
                                        document.getElementById('loggedInFullname').value = userDetails.name;
                                        document.getElementById('loggedInEmail').value = userDetails.email;
                                        document.getElementById('loggedInPhone').value = userDetails.phone;
                                    } else {
                                        alert('User details fetch failed');
                                    }
                                })
                                .catch(error => {
                                    // Handle network errors
                                });
                            // Display the user details in the modal body


                            // Show the modal
                            $('#userDetailsModal').modal('show');
                        }
                    });
                </script>
            </div>
            <div class="modal fade" id="userDetailsModal" tabindex="-1" role="dialog"
                 aria-labelledby="userDetailsModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="userDetailsModalLabel">User Details</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label for="loggedInUserId">UserId</label>
                                    <input type="text" class="form-control" id="loggedInUserId" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="loggedInNickname">Nickname</label>
                                    <input type="text" class="form-control" id="loggedInNickname">
                                </div>
                                <div class="form-group">
                                    <label for="loggedInFullname">Full Name</label>
                                    <input type="text" class="form-control" id="loggedInFullname">
                                </div>
                                <div class="form-group">
                                    <label for="loggedInEmail">Email</label>
                                    <input type="email" class="form-control" id="loggedInEmail">
                                </div>
                                <div class="form-group">
                                    <label for="loggedInPhone">Phone</label>
                                    <input type="text" class="form-control" id="loggedInPhone">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" id="editBtnModal">Edit
                            </button>
                            <script>
                                document.getElementById('editBtnModal').addEventListener('click', function () {
                                    const id = JSON.parse(localStorage.getItem('user'));
                                    const nickname = document.getElementById('loggedInNickname').value;
                                    const fullname = document.getElementById('loggedInFullname').value;
                                    const email = document.getElementById('loggedInEmail').value;
                                    const phone = document.getElementById('loggedInPhone').value;
                                    const user = JSON.parse(localStorage.getItem('user'));
                                    user.nickname = nickname;
                                    user.name = fullname;
                                    user.email = email;
                                    user.phone = phone;

                                    fetch('/user/' + id, {
                                        method: 'PUT',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify({
                                            userId: id,
                                            nickName: nickname,
                                            name: fullname,
                                            email: email,
                                            phone: phone

                                        }),
                                    }).then(response => response.json())
                                        .then(data => {
                                            console.log(data)
                                            if (data.status === "success") {
                                                alert('User details updated successfully');
                                                localStorage.setItem('user', JSON.stringify(user));
                                            } else {
                                                alert('User details update failed');
                                            }
                                        })
                                        .catch(error => {
                                            // Handle network errors
                                        });
                                });
                            </script>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" id="withdrawBtn">
                                Withdraw
                            </button>
                            <script>
                                document.getElementById('withdrawBtn').addEventListener('click', function () {
                                    const id = JSON.parse(localStorage.getItem('user'));
                                    fetch('/user/' + id, {
                                        method: 'DELETE',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        }
                                    }).then(response => response.json())
                                        .then(data => {
                                            if (data.status === "success") {
                                                alert('User deleted successfully');
                                                localStorage.removeItem('token');
                                                localStorage.removeItem('user');
                                                $('#loginButton').text('Login');
                                                $('#user_id').text('Guest');
                                            } else {
                                                alert('User delete failed');
                                            }
                                        })
                                        .catch(error => {
                                            // Handle network errors
                                        });
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div style="padding-top: 12.5px">
            <button                     class="bg-primary" id="loginButton"
                                        style="width: 138px; height: 60px; padding: 18px 40px 18px 40px; border-radius: 8px; background-color: #005BE2">
                ${localStorage.getItem('token') ? 'Logout' : 'Login'}
            </button>
            <script type="text/javascript">
                document.getElementById('loginButton').addEventListener('click', function (event) {
                    event.preventDefault();
                    if (localStorage.getItem('token')) {
                        localStorage.removeItem('token');
                        localStorage.removeItem('user');
                        $('#loginButton').text('Login');
                        $('#user_id').text('Guest');
                        return;
                    }
                    $('#loginModal').modal('show');
                });
            </script>

        </div>
    </div>
</div>
<div class="banner-container"
     style="width: 1132px; height: 212px; gap:60px; justify-self: center; margin-top: 150px; margin-left: 154px">
    <div class="banner" style="width: 1132px; height: 92px;">
        <div style="width:1132px; font-size: 32px; font-weight: 600; line-height: 38.41px; align-items: center; height: 38px">
            54DNAtype's learning gene type test is divided into 9 groups
        </div>
        <div style="width:1132px; font-size: 32px; font-weight: 600; line-height: 38.41px; align-items: center; height: 38px">
            and presents 54 type results.
        </div>
    </div>
    <div style="display: flex; margin-left: 400px; padding-top: 40px">
        <button class="bg-primary"
                style="width: 138px; height: 60px; border-radius: 8px; background-color: #005BE2">
            Main
        </button>
    </div>
</div>

<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">Login</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" id="username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="loginBtnModal">Login</button>
                <script type="text/javascript">
                    document.getElementById('loginBtnModal').addEventListener('click', function () {
                        const username = document.getElementById('username').value;
                        const password = document.getElementById('password').value;
                        fetch('/user/login', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                userName: username,
                                password: password
                            })
                        })
                            .then(response => response.json())
                            .then(data => {
                                if (data.status === "success") {
                                    $('#loginModal').modal('hide');
                                    localStorage.setItem('token', data.data.token);
                                    localStorage.setItem('user', JSON.stringify(data.data.userId));
                                    $('#user_id').text(username)
                                    $('#loginButton').text('Logout');


                                } else {
                                    alert('Login failed');
                                }
                            })
                            .catch(error => {
                                // Handle network errors
                            });
                    });
                </script>
                <button type="button" class="btn btn-primary" id="registerBtnModal">Register</button>
            </div>
        </div>
    </div>
</div>

<!-- Register Modal -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerModalLabel">Register</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="registerUsername">Username</label>
                        <input type="text" class="form-control" id="registerUsername">
                    </div>
                    <div class="form-group">
                        <label for="registerPassword">Password</label>
                        <input type="password" class="form-control" id="registerPassword">
                    </div>
                    <div class="form-group">
                        <label for="registerNickname">Nickname</label>
                        <input type="text" class="form-control" id="registerNickname">
                    </div>
                    <div class="form-group">
                        <label for="registerFullname">Full Name</label>
                        <input type="text" class="form-control" id="registerFullname">
                    </div>
                    <div class="form-group">
                        <label for="registerEmail">Email</label>
                        <input type="email" class="form-control" id="registerEmail">
                    </div>
                    <div class="form-group">
                        <label for="registerPhone">Phone</label>
                        <input type="text" class="form-control" id="registerPhone">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="registerBtnModalSubmit">Register</button>
                <script>
                    document.getElementById('registerBtnModalSubmit').addEventListener('click', function () {
                        fetch('/email/send', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                to: document.getElementById('registerEmail').value
                            })
                        })
                            .then(response => response.json())
                            .then(data => {
                                if (data.status === "success") {
                                    const userId = document.getElementById('registerUsername').value;
                                    const password = document.getElementById('registerPassword').value;
                                    const nickname = document.getElementById('registerNickname').value;
                                    const fullname = document.getElementById('registerFullname').value;
                                    const email = document.getElementById('registerEmail').value;
                                    const phone = document.getElementById('registerPhone').value;
                                    localStorage.setItem('user',
                                        JSON.stringify({
                                            password: password,
                                            nickname: nickname,
                                            name: fullname,
                                            email: email,
                                            phone: phone,
                                            userId: userId
                                        })
                                    );
                                    $('#otpModal').modal('show');
                                } else {
                                    alert('Email failed');
                                }
                            })
                            .catch(error => {
                                // Handle network errors
                            });
                    });
                </script>
            </div>
        </div>
    </div>
</div>

<!-- OTP Verification Modal -->
<div class="modal fade" id="otpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="otpModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="otpModalLabel">OTP Verification</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="otp">Enter OTP:</label>
                        <input type="text" class="form-control" id="otp">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="verifyOtpBtn">Verify OTP</button>
                <script>
                    document.getElementById('verifyOtpBtn').addEventListener('click', function () {
                        const otp = document.getElementById('otp').value;
                        fetch('/otp/validate?otp=' + otp, {
                            method: 'GET',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                        })
                            .then(response => response.json())
                            .then(data => {
                                if (data.status === "success") {
                                    const user = JSON.parse(localStorage.getItem('user'));
                                    fetch('/user/signup', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        },
                                        body: JSON.stringify({
                                            userId: user.userId,
                                            password: user.password,
                                            nickName: user.nickname,
                                            name: user.name,
                                            email: user.email,
                                            phone: user.phone
                                        })
                                    })
                                        .then(response => response.json())
                                        .then(data => {
                                            if (data.status === "success") {
                                                $('#otpModal').modal('hide');
                                                $('#registerModal').modal('hide');
                                                $('#loginModal').modal('show');
                                            } else {
                                                alert('Register failed');
                                            }
                                        })
                                        .catch(error => {
                                            // Handle network errors
                                        });
                                } else {
                                    alert('OTP verification failed :', data.message);
                                }
                            })
                            .catch(error => {
                                // Handle network errors
                            });
                    });
                </script>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
<script type="text/javascript">
    // Prevent modal disappearance on input click
    $('#loginModal').on('shown.bs.modal', function () {
        $('#username').focus();
    });

    // Fix register modal opening issue
    document.getElementById('registerBtnModal').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent default form submission
        $('#loginModal').modal('hide'); // Hide login modal
        $('#registerModal').modal('show'); // Show register modal
    });

    // Other event listeners and scripts
</script>
</body>
</html>
