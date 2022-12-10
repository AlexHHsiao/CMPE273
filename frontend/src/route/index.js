import React from "react";
import {Switch, Route} from "react-router-dom";
import ProtectedRoute from "./protected-route";
import Dashboard from "../view/dashboard";
import Login from "../view/login";
import NotFound from "../view/404";
import HomeDetail from "../view/home-detail";
import AdminDashboard from "../view/admin-dashboard";
import Pending from "../view/pending";
import Profile from "../view/profile";
import LandingPage from "../view/landing";
import Favorite from "../view/favorite";

const AppRoutes = () => (
  <LandingPage>
    <Switch>
      <ProtectedRoute path="/" exact component={Dashboard} />
      <ProtectedRoute path="/admin" exact component={AdminDashboard} />
      <ProtectedRoute path="/profile" exact component={Profile} />
      <ProtectedRoute path="/home" exact component={HomeDetail} />
      <ProtectedRoute path="/favorite" exact component={Favorite} />
      <Route path="/pending" exact component={Pending} />
      <Route path="/login" exact component={Login} />
      <Route path="*" component={NotFound} />
    </Switch>
  </LandingPage>
);

export default AppRoutes;
