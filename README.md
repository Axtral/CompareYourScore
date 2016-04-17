# CompareYourScore

## Présentation
Cette application permet de créer des challenges avec ses amis et des gens du monde entier. 
Il s'agit d'un projet de cours de développement Android, les notions suivantes sont mises en jeu : 

* Cache en base de donnée locale
* Cache d'image en utilisant un code externe
* Mise en place de templates xml
* Gestion des permissions
* Traduction en fonction de la localisation
* ListView
* ViewPager et Fragements
* Listeners
* Communications avec un serveur
* Parsing JSON
* Communication entre activités
* Animation personnalisée
* Gestion des exceptions
* ...

##Installation
Afin de ne pas souffrir de problèmes de compatibilité lié à Android Studio et son système de compilation, nous avons opté pour ne mettre que les sources sur le dépôt. La manipulation pour configurer un nouveau projet est celle-ci : 

1. Créer un nouveau projet. 
	* Le nom de domaine est rougevincloud.com
	* Le nom de l'application est Chat
	* Ne créez pas d'activité
2. Configurer git :
	* Ouvrir le terminal
	*  `cd app/src/main`
	* `rm -rf *`
	* `git init`    
	* `git remote add origin https://github.com/LouisGerard/CompareYourScore.git`
	* `git pull origin master`

Ainsi, votre projet aura une configuration adaptée à votre machine. Si vous voulez intégrer git dans Android Studio : 

3. Définir la VCS root
	* File > Settings > Version Control > +
	* Choisissez le dossier `/chemin_vers_votre_workspace/Chat/app/src/main`
	* Choisissez git dans le champs VCS
	* Validez