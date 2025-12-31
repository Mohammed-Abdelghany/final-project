import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from "../security/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.authService.isUserLogin()) {
      const rolesString = sessionStorage.getItem("userRoles");
      if (rolesString) {
        try {
          if (rolesString.includes("ADMIN")) {
            return true;
          }
        } catch (e) {
          console.error("Failed to parse user roles from sessionStorage", e);
        }
      }
    }
    this.router.navigate(['/products'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
