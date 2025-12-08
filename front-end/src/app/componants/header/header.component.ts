import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from "../../../service/security/auth.service";
import { CategoryService } from "../../../service/category.service";
import { Observable } from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  categories$: Observable<any>;

  constructor(
    private router: Router,
    private authService: AuthService,
    private categoryService: CategoryService
  ) {}

  ngOnInit() {
    this.categories$ = this.categoryService.getCategories(1,4);
  }

  search(value: string) {
    this.router.navigateByUrl('/search/' + value);
  }

  isUserLogin(): boolean {
    return this.authService.isUserLogin();
  }

  logout() {
    this.router.navigateByUrl('/login');
    this.authService.logout();
  }
}
