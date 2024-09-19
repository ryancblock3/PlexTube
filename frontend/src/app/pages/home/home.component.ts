import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface Video {
  title: string;
  channelName: string;
  thumbnailUrl: string;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  recentVideos: Video[] = [
    { title: 'Understanding Angular Basics', channelName: 'CodeMaster', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=Angular+Basics' },
    { title: 'Advanced JavaScript Techniques', channelName: 'JS Guru', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=Advanced+JS' },
    { title: 'Building RESTful APIs with Node.js', channelName: 'Backend Pro', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=Node.js+APIs' },
    { title: 'CSS Grid Layout Mastery', channelName: 'FrontEnd Wizard', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=CSS+Grid' },
    { title: 'TypeScript for Beginners', channelName: 'TypeMaster', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=TypeScript' },
    { title: 'React Hooks Explained', channelName: 'React Ninja', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=React+Hooks' },
    { title: 'Docker for Developers', channelName: 'DevOps Journey', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=Docker' },
    { title: 'Machine Learning with Python', channelName: 'AI Explorer', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=ML+Python' },
    { title: 'GraphQL vs REST', channelName: 'API Architect', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=GraphQL+vs+REST' },
    { title: 'Vue.js Component Design', channelName: 'Vue Virtuoso', thumbnailUrl: 'https://via.placeholder.com/640x360.png?text=Vue.js+Components' }
  ];

  constructor() { }

  ngOnInit(): void {
    // In the future, you might fetch real data here
  }
}
