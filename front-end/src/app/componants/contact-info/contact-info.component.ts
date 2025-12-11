import { Component, OnInit } from '@angular/core';
import {MessageContactService} from "../../../service/message-contact.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-contact-info',
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent implements OnInit {
   message_contact:MessageContactService;
  private router: Router

  constructor(message_contact:MessageContactService,router: Router) {
    this.router=router;
    this.message_contact=message_contact;
  }

  ngOnInit(): void {
  }
  sendMessage( message: string, subject: string) {
    this.message_contact.sendMessage(message, subject).subscribe(
      response => {
        console.log('Message sent successfully', response);
        alert("Message sent successfully");
        this.router.navigate(['/']);

      },
      error => {
        console.error('Error sending message', error);
        alert("Error sending message");
      }
    );

  }
}
