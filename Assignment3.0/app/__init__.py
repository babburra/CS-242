from flask import Flask
#from app import view
import os

from flask import Flask, render_template
from parse_list import parse_list
from parse_log import parse_log
from subprocess import call
from flask import jsonify
app = Flask(__name__, static_url_path='')


@app.route('/')
def root():
    return "Hello, World!"  # return a string


@app.route("/index.html")
def index():
    return render_template("index.html")


@app.route("/projects.html")
def projects():
    return render_template("projects.html")


@app.route("/about.html")
def about():
    return render_template("about.html")


@app.errorhandler(404)
def not_found(error):
    return render_template('404.html'), 404

@app.route('/get_log')
def get_log():
    call("svn log --verbose --xml https://subversion.ews.illinois.edu/svn/fa16-cs242/ywang443 > app/svn_log.xml",
         shell=True)
    project_names = []
    filename = 'app/svn_log.xml'
    info = parse_log(filename)
    return jsonify(info.items())


@app.route('/load_projects')
def load_projects():
    #clear the database
#    Directory.query.delete()
#    File.query.delete()
#    SubVersion.query.delete()
    #down load xml file
    call("svn log --verbose --xml https://subversion.ews.illinois.edu/svn/fa16-cs242/ywang443 > app/svn_log.xml",shell=True)
    call("svn list --xml --recursive https://subversion.ews.illinois.edu/svn/fa16-cs242/ywang443 > app/svn_list.xml",shell=True)
    #populate models
    #initialize_list('app/svn_list.xml')
    #initialize_log('app/svn_log.xml')
    #get_most_project_msg()
    #return redirect(url_for('show_entries'))
    return get_log()

if __name__ == "__main__":
    app.run()



