import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Chef } from '../model/chef';
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ChefService {
  private baseUrl = 'http://localhost:9090/users'; // عدّل حسب API

  constructor(private http: HttpClient) {}

  getAllChefs(page: number, size: number): Observable<Chef[]> {
    return this.http.get<any>(`${this.baseUrl}/chefs?page=${page}&size=${size}`)
      .pipe(
        map(response => {
          const data = response.content ? response.content : response;

          return data.map((item: any) => ({
            fullName: item.name || '',
            logoPath: item.logoPath || 'assets/img/default.png',
            speciality: item.specs || '',
            facebookUrl: item.face_link || '#',
            instagramUrl: item.inst_link || '#',
            twitterUrl: item.twe_link || '#'
          }))
        })
      );
  }

}
