import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MessageContactService {

  private baseUrl = 'http://localhost:9090/contact-messages'; // عدّل حسب API

  constructor(private http: HttpClient) {}
  sendMessage( message: string, subject: string) {
    const payload = { message,subject };
    return this.http.post(`${this.baseUrl}`, payload);
  }
}
