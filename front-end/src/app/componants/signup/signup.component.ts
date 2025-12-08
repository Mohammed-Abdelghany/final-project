import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../service/security/auth.service";
import {Router} from "@angular/router";
import {error} from "protractor";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  messageAr: string;
  messageEn: string;
  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  createAccount(userName: string, name: string, password: string, confirmPassword: string) {
    // Clear messages after 3 seconds
    setTimeout(() => {
      this.messageAr = '';
      this.messageEn = '';
    }, 3000);

    // Validate inputs before sending request
    if (!this.validateInputs(userName, name, password, confirmPassword)) {
      return;
    }

    // Call signup service
    this.authService.signup(userName, name, password).subscribe({
      next: (response) => {
        // Save data in session storage
        sessionStorage.setItem('userName', response.username);
        sessionStorage.setItem('password', response.password);
        sessionStorage.setItem('name', response.name ?? name);
        sessionStorage.setItem('roles', JSON.stringify(response.roles));

        // Navigate to products page
        this.router.navigateByUrl('/products');
      },
      error: (errorResponse) => {
        console.log(errorResponse);

        if (errorResponse.error.length > 0) {
          this.messageEn = errorResponse.error[0].message_en ;
          this.messageAr = errorResponse.error[0].message_ar ;
        } else {
          this.messageEn = 'Something went wrong';
          this.messageAr = 'حدث خطأ ما';
        }
      }
    });
  }

  private validateInputs(userName: string,name:string, password: string, confirmPassword: string): boolean {
    if (!userName) {
      this.messageAr = "من فضلك أدخل اسم المستخدم";
      this.messageEn = "Please enter a username";
      return false;
    }

    if (!name) {
      this.messageAr = "من فضلك أدخل اسمك";
      this.messageEn = "Please enter a name";
      return false;
    }
    if (!password) {
      this.messageAr = "من فضلك أدخل كلمة المرور";
      this.messageEn = "Please enter a password";
      return false;

    }
    if (password !== confirmPassword) {
      this.messageAr = "كلمة المرور وتأكيد كلمة المرور غير متطابقين";
      this.messageEn = "Password and Confirm Password do not match";
      return false;
    }
    return true;
  }



  }
