# Git and GitHub Workflow   
  
## 1. Creating a New Project (Local Setup)  
  
### Project Structure  
  
```
project-name/  
│  
├── src/          # Source code  
├── README.md     # Project documentation  
├── .gitignore    # Ignore compiled and IDE files  
```  
  
Best practices:  
- Keep compiled folders (bin/, target/, out/) inside `.gitignore`  
- Keep README structured and descriptive  
  
---  
  
## 2. Initialize Git Repository  
  
Check repository status:  
  
`git status`  
  
If not initialized:  
  
`git init`  
  
This enables Git tracking in the folder.  
  
---  
  
## 3. Stage Files  
  
Stage all files:  
  
`git add .`  
  
Or selectively:  
  
`git add README.md`  
`git add src/`  
  
---  
  
## 4. Commit Changes  
  
`git commit -m "Initial project setup"`  
  
Commit message should describe what changed and why, not just the action performed.  
  
Good examples:  
- "Add payment module structure"  
- "Refactor service layer"  
    
Commonly used commit types (Conventional Commits style):  
  
- feat: introduce a new feature  
- fix: bug fix  
- refactor: internal code improvement without changing behavior  
- docs: documentation updates  
- chore: maintenance or non-functional changes  
    
Avoid:  
- "updated"  
- "changes"  
- "fixed stuff"  
  
---  
  
## 5. Configure Git (One-Time Setup Per System)  
  
`git config --global user.name "Your Name"`  
`git config --global user.email "your@email.com"`  
  
This only needs to be done once per machine.  
  
---  
  
# Connecting to GitHub  
  
After creating an empty repository on GitHub:  
  
## 6. Rename Default Branch  
  
`git branch -M main`  
  
---  
  
## 7. Add Remote Repository  
  
`git remote add origin https://github.com/username/repository.git`  
  
---  
  
## 8. Push and Set Upstream  
  
`git push -u origin main`  
  
The -u flag sets upstream tracking. Future pushes require only:  
  
`git push`  
  
---  
  
# Daily Workflow After Setup  
  
For any meaningful update:  
  
`git add .`  
`git commit -m "Describe what changed and why"`  
`git push`  
  
---  
  
# Useful Alternatives  
  
## Commit Modified Files Only  
  
`git commit -am "message"`  
  
Note:  
This does NOT stage new untracked files.  
  
---  
  
## Temporarily Save Changes (Stash)  
  
`git stash`  
  
Restore:  
  
`git stash pop`  
  
Git stash follows LIFO (Last In, First Out).  
  
---  
  
# Core Git Concepts  

Branch:  
An independent line of development used to work on features or fixes separately.  
  
Repository:  
A folder tracked by Git.  
  
Staging Area:  
Intermediate space before committing.  
  
Commit:  
Snapshot of changes at a point in time.  
  
Remote:  
GitHub repository connected to local repository.  
  
Upstream:  
Default remote branch linked to local branch.  
  